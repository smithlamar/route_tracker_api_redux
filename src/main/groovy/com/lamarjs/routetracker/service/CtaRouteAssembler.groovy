package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
class CtaRouteAssembler {

    CtaApiRequestService ctaApiRequestService

    @Autowired
    CtaRouteAssembler(CtaApiRequestService ctaApiRequestService) {
        this.ctaApiRequestService = ctaApiRequestService
    }

    List<Route> initializeRoutes() {

        List<Route> routes = ctaApiRequestService.getRoutes()

        log.debug("Route id is ${routes.get(0).getRouteId()}")

        routes.forEach({ route ->

            List<Direction> directions = ctaApiRequestService.getDirections(route.getRouteId())

            route.setStops(new LinkedHashMap<Direction, List<Stop>>(100))
            directions.forEach({ direction ->

                List<Stop> stops = ctaApiRequestService.getStops(route.getRouteId(), direction)
                route.getStops().put(direction, stops)
            })
        })

        return routes
    }
}
