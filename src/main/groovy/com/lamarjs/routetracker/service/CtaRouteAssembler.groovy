package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.bus.DirectionsApiResponse
import com.lamarjs.routetracker.model.cta.api.bus.RoutesApiResponse
import com.lamarjs.routetracker.model.cta.api.bus.StopsApiResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class CtaRouteAssembler {

    private CtaApiRequestService ctaApiRequestService
    private CtaApiUriBuilder ctaApiUriBuilder

    CtaRouteAssembler(CtaApiRequestService ctaApiRequestService, CtaApiUriBuilder ctaApiUriBuilder) {
        this.ctaApiRequestService = ctaApiRequestService
        this.ctaApiUriBuilder = ctaApiUriBuilder
    }

    List<Route> initializeRoutes() {

        List<Route> routes = ctaApiRequestService.sendRoutesRequest(ctaApiUriBuilder.buildRoutesUri());

        println("Route id is ${routes.get(0).getRouteId()}")

        routes.forEach({ Route route ->

            List<Direction> directions = ctaApiRequestService.
                    sendDirectionsRequest(ctaApiUriBuilder.buildDirectionsUri(route.getRouteId()))

            route.setStops(new LinkedHashMap<Direction, List<Stop>>(100))

            directions.forEach({ direction ->
                List<Stop> stops = ctaApiRequestService.
                        sendStopsRequest(ctaApiUriBuilder.buildStopsUri(route.getRouteId(), direction))
                route.getStops().put(direction, stops)
            })
        })

        return routes
    }
}
