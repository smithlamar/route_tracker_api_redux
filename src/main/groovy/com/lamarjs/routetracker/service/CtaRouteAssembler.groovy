package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import com.lamarjs.routetracker.persistence.SavedRoutesFileManager
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime
import java.time.ZoneOffset

@Slf4j
class CtaRouteAssembler {

    CtaApiRequestService ctaApiRequestService
    SavedRoutesFileManager savedRoutesFileManager
    Map<String, Route> assembledRoutes

    @Autowired
    CtaRouteAssembler(CtaApiRequestService ctaApiRequestService, SavedRoutesFileManager savedRoutesFileManager) {
        this.ctaApiRequestService = ctaApiRequestService
        this.savedRoutesFileManager = savedRoutesFileManager
    }

    List<Route> initializeRoutes() {

        List<Route> initializedRoutes = new ArrayList<>()

        if (savedRoutesFileManager.savedRoutesFileIsStale()) {
            initializedRoutes = loadRoutesFromCtaApi()
            savedRoutesFileManager.saveRoutes(initializedRoutes)
        } else {
            initializedRoutes = savedRoutesFileManager.loadRoutes()
            Long routeCreationTime = initializedRoutes.get(0).getCreatedDateInEpochSeconds()

            if (SavedRoutesFileManager.isOlderThanSevenDays(routeCreationTime)) {
                initializedRoutes = loadRoutesFromCtaApi()
                savedRoutesFileManager.saveRoutes(initializedRoutes)
            }
        }

        assembledRoutes = buildRoutesMap(initializedRoutes)
        return initializedRoutes
    }

    private static Map<String, Route> buildRoutesMap(List<Route> routes) {
        Map<String, Route> routesMap = new HashMap<>()
        routes.forEach({ route ->
            routesMap.put(route.routeId, route)
        })
        return routesMap
    }

    List<Route> loadRoutesFromCtaApi() {

        List<Route> routes = ctaApiRequestService.getRoutes()

        routes.parallelStream().forEach({ route ->
            route.setCreatedDateInEpochSeconds(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            List<Direction> directions = ctaApiRequestService.getDirections(route.getRouteId())

            route.setStops(new ArrayList<Stop>())
            directions.parallelStream().forEach({ direction ->

                List<Stop> stops = ctaApiRequestService.getStops(route.getRouteId(), direction)
                stops.parallelStream().forEach({ stop ->
                    stop.setDirection(direction.toString())
                })
                route.getStops().addAll(stops)
            })
        })

        return routes
    }
}
