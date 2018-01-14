package com.lamarjs.routetracker.controller

import com.lamarjs.routetracker.data.cta.api.common.Prediction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.exception.CtaApiException
import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.service.CtaRouteAssembler
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class RouteTrackerApiController {

    CtaRouteAssembler ctaRouteAssembler
    CtaApiRequestService ctaApiRequestService

    @Autowired
    RouteTrackerApiController(CtaRouteAssembler ctaRouteAssembler, CtaApiRequestService ctaApiRequestService) {
        this.ctaRouteAssembler = ctaRouteAssembler
        this.ctaApiRequestService = ctaApiRequestService
    }

    @RequestMapping(value = "/routes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Route> getRoutes() {
        return ctaRouteAssembler.getAssembledRoutes().values().toList()
    }

    @RequestMapping(value = "/predictions/{routeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
        catch (CtaApiException ex) {
            status = getStatusBasedOnException(ex)
            responseBody = JsonOutput.toJson(errors: ex.getErrors())
        }
        catch (Exception ex) {
            status = getStatusBasedOnException(ex)
            responseBody = JsonOutput.toJson(errors: ex.getMessage())
        }

        return new ResponseEntity<String>(responseBody, status)
    }

    static HttpStatus getStatusBasedOnException(Exception ex) {

        String simplifiedExceptionMessage = ex.getMessage().replace('[', '').replace(']', '')

        switch (simplifiedExceptionMessage) {

            case CtaApiRequestService.CtaErrorMessageConstants.BAD_PARAMETER:
                return HttpStatus.BAD_REQUEST

            case CtaApiRequestService.CtaErrorMessageConstants.NO_SERVICE_SCHEDULED:
                return HttpStatus.NOT_FOUND

            default:
                return HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}
