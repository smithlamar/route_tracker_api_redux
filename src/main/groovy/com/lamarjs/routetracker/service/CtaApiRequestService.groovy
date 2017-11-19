package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.bus.BustimeApiResponse
import com.lamarjs.routetracker.model.cta.api.bus.DirectionsApiResponse
import com.lamarjs.routetracker.model.cta.api.bus.PredictionsApiResponse
import com.lamarjs.routetracker.model.cta.api.bus.RoutesApiResponse
import com.lamarjs.routetracker.model.cta.api.bus.StopsApiResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Prediction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop
import groovy.util.logging.Slf4j
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Slf4j
@Service
class CtaApiRequestService {

    private RestTemplate restTemplate

    CtaApiRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    List<Route> sendRoutesRequest(URI uri) {

        ResponseEntity<RoutesApiResponse> responseEntity = restTemplate.
                exchange(uri, HttpMethod.GET, null, RoutesApiResponse)

        log.info("CTA Bustime API request returned status code: ${responseEntity.getStatusCodeValue()}")

        RoutesApiResponse response = responseEntity.getBody()
        reportErrors(response)

        return response.getPayloadTargetEntity()
    }

    List<Direction> sendDirectionsRequest(URI uri) {

        ResponseEntity<DirectionsApiResponse> responseEntity = restTemplate.
                exchange(uri, HttpMethod.GET, null, DirectionsApiResponse)

        log.info("CTA Bustime API request returned status code: ${responseEntity.getStatusCodeValue()}")

        DirectionsApiResponse response = responseEntity.getBody()
        reportErrors(response)

        return response.getPayloadTargetEntity()
    }

    List<Stop> sendStopsRequest(URI uri) {

        ResponseEntity<StopsApiResponse> responseEntity = restTemplate.
                exchange(uri, HttpMethod.GET, null, StopsApiResponse)

        log.info("CTA Bustime API request returned status code: ${responseEntity.getStatusCodeValue()}")

        StopsApiResponse response = responseEntity.getBody()
        reportErrors(response)

        return response.getPayloadTargetEntity()
    }

    List<Prediction> sendPredictionsRequest(URI uri) {

        ResponseEntity<PredictionsApiResponse> responseEntity = restTemplate.
                exchange(uri, HttpMethod.GET, null, PredictionsApiResponse)

        log.info("CTA Bustime API request returned status code: ${responseEntity.getStatusCodeValue()}")

        PredictionsApiResponse response = responseEntity.getBody()
        reportErrors(response)

        return response.getPayloadTargetEntity()
    }

    void reportErrors(BustimeApiResponse response) throws Exception {

        if (response.hasErrors()) {
            log.error("Get request resulted in ${response.getErrors().size()} error(s)")
            throw new Exception(response.getErrorMessages().toListString())
        }
    }
}
