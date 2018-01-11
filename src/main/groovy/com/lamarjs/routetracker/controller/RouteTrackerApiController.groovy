package com.lamarjs.routetracker.controller

import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.service.CtaRouteAssembler
import org.springframework.beans.factory.annotation.Autowired
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

}
