package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import com.lamarjs.routetracker.persistence.RouteRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime
import java.time.ZoneOffset

@Slf4j
class CtaRouteAssembler {

    CtaApiRequestService ctaApiRequestService
    RouteRepository routeRepository
    Map<String, Route> routesMap

    @Autowired
    CtaRouteAssembler(CtaApiRequestService ctaApiRequestService, RouteRepository routeRepository) {
        this.ctaApiRequestService = ctaApiRequestService
        this.routeRepository = routeRepository
    }

    List<Route> initializeRoutes() {

        List<Route> initializedRoutes = new ArrayList<>()

        if (!routesMap && routeRepository.isStale()) {

            log.info("Assembled routes were either stale or non-existent. Initializing from CTA API.")
            initializedRoutes = loadRoutesFromCtaApi()
            log.debug("Loaded routes from CTA API.")

            routeRepository.saveRoutes(initializedRoutes)
        } else {
            log.info("Initializing routes from route repository.")
            initializedRoutes = routeRepository.getRoutes()
        }

        routesMap = buildRoutesMap(initializedRoutes)
        return initializedRoutes
    }

    static Map<String, Route> buildRoutesMap(List<Route> routes) {
        Map<String, Route> routesMap = new HashMap<>()
        routes.forEach({ route ->
            routesMap.put(route.routeId, route)
        })
        return routesMap
    }

    List<Route> loadRoutesFromCtaApi() {

        List<Route> routes = ctaApiRequestService.getRoutes()
        List<Route> syncRoutes = Collections.synchronizedList(routes)

        synchronized (syncRoutes) {

            syncRoutes.parallelStream().forEach({ route ->
                route.setCreatedDateInEpochSeconds(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))

                List<Direction> directions = ctaApiRequestService.getDirections(route.getRouteId())

                route.setStops(new ArrayList<Stop>())

                directions.parallelStream().forEach({ direction ->

                    List<Stop> stops = ctaApiRequestService.getStops(route.getRouteId(), direction)
                    List<Stop> stopsSync = Collections.synchronizedList(stops)

                    synchronized (stopsSync) {
                        stopsSync.parallelStream().forEach({ stop ->
                            stop.setDirection(direction.toString())
                        })
                    }
                    route.getStops().addAll(stops)
                })
            })
        }


        return routes
    }
}
