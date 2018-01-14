package com.lamarjs.routetracker.controller

import com.lamarjs.routetracker.data.cta.api.common.Prediction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.service.CtaRouteAssembler
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    public ResponseEntity<String> getPredictions(
            @PathVariable String routeId,
            @RequestParam String stopId, @RequestParam(required = false) Integer resultsLimit) {

        HttpStatus status
        String responseBody

        try {
            List<Prediction> predictions = resultsLimit ? ctaApiRequestService.getPredictions(routeId, stopId, resultsLimit) : ctaApiRequestService
                    .getPredictions(routeId, stopId)
            responseBody = JsonOutput.toJson(predictions)
            status = HttpStatus.OK
        }
        catch (Exception ex) {
            status = getStatusBasedOnException(ex)
            // The message is already in Json format
            responseBody = ex.getMessage()
        }

        return new ResponseEntity<String>(responseBody, status)
    }

    static HttpStatus getStatusBasedOnException(Exception ex) {

        String message = ex.getMessage().toLowerCase()

        switch (message) {

            case message.contains(CtaApiRequestService.CtaErrorMessageConstants.BAD_PARAM.message):
                return HttpStatus.BAD_REQUEST

            case message.contains(CtaApiRequestService.CtaErrorMessageConstants.NO_SERVICE_SCHEDULED.message):
                return HttpStatus.NO_CONTENT

            default:
                return HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}
