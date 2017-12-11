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
    CtaApiUriBuilder ctaApiUriBuilder

    @Autowired
    CtaRouteAssembler(CtaApiRequestService ctaApiRequestService, CtaApiUriBuilder ctaApiUriBuilder) {
        this.ctaApiRequestService = ctaApiRequestService
        this.ctaApiUriBuilder = ctaApiUriBuilder
    }

    List<Route> initializeRoutes() {

        List<Route> routes = ctaApiRequestService.sendGetRequest(ctaApiUriBuilder.buildRoutesUri()).getRoutes()

        log.debug("Route id is ${routes.get(0).getRouteId()}")

        routes.forEach({ route ->

            List<Direction> directions = ctaApiRequestService.
                    sendGetRequest(ctaApiUriBuilder.buildDirectionsUri(route.getRouteId())).getDirections()

            route.setStops(new LinkedHashMap<Direction, List<Stop>>(100))

            directions.forEach({ direction ->

                List<Stop> stops = ctaApiRequestService.
                        sendGetRequest(ctaApiUriBuilder.buildStopsUri(route.getRouteId(), direction)).getStops()

                route.getStops().put(direction, stops)
            })
        })

        return routes
    }
}
