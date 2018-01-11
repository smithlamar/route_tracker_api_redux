package com.lamarjs.routetracker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import com.lamarjs.routetracker.persistence.SavedRoutesFileManager
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository

@Slf4j
class CtaRouteAssembler {

    CtaApiRequestService ctaApiRequestService
    SavedRoutesFileManager savedRoutesFileManager
    Map<String, Route> assembledRoutes

    @Autowired
    CtaRouteAssembler(CtaApiRequestService ctaApiRequestService, SavedRoutesFileManager savedRoutesFileManager) {
        this.ctaApiRequestService = ctaApiRequestService
    }

    List<Route> initializeRoutes() {

        if (savedRoutesFileManager.savedRoutesFileIsStale()) {

            List<Route> initializedRoutes
            initializedRoutes = getRoutesFromCtaApi()

            savedRoutesFileManager.saveRoutes(initializedRoutes)
            buildRoutesMap(initializedRoutes)

            return initializedRoutes
        }

        return savedRoutesFileManager.loadRoutes()
    }

    private void buildRoutesMap(List<Route> routes) {
        routes.forEach({route ->
            assembledRoutes.put(route.routeId, route)
        })
    }

    List<Route> getRoutesFromCtaApi() {

        List<Route> routes = ctaApiRequestService.getRoutes()

        log.debug("Route id is ${routes.get(0).getRouteId()}")

        routes.forEach({ route ->

            List<Direction> directions = ctaApiRequestService.getDirections(route.getRouteId())

            route.setStops(new ArrayList<Stop>())
            directions.forEach({ direction ->

                List<Stop> stops = ctaApiRequestService.getStops(route.getRouteId(), direction)
                stops.forEach({stop ->
                    stop.setDirection(direction)
                })
                route.getStops().addAll(stops)
            })
        })

        return routes
    }
}
