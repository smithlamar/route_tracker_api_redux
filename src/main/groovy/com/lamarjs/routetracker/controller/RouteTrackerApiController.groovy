package com.lamarjs.routetracker.controller

import com.lamarjs.routetracker.data.cta.api.common.Prediction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.service.CtaRouteAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RouteTrackerApiController {
    CtaRouteAssembler ctaRouteAssembler
    CtaApiRequestService ctaApiRequestService

    @Autowired
    RouteTrackerApiController(CtaRouteAssembler ctaRouteAssembler, CtaApiRequestService ctaApiRequestService) {
        this.ctaRouteAssembler = ctaRouteAssembler
        this.ctaApiRequestService = ctaApiRequestService
    }

    @GetMapping("/routes")
    public List<Route> getRoutes() {
        return ctaRouteAssembler.getAssembledRoutes().values().toList()
    }

    @GetMapping("/predictions/{routeId}")
    public List<Prediction> getPredictions(
            @PathVariable String routeId,
            @RequestParam String stopId, @RequestParam(required = false) Integer resultsLimit) {
        
        return resultsLimit ? ctaApiRequestService.getPredictions(routeId, stopId, resultsLimit) : ctaApiRequestService
                .getPredictions(routeId, stopId)

    }

}
