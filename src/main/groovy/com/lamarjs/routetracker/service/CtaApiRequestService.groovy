package com.lamarjs.routetracker.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.data.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Prediction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES

@Slf4j
class CtaApiRequestService {

    RestTemplate restTemplate
    ObjectMapper objectMapper
    CtaApiUriBuilder ctaApiUriBuilder

    @Autowired
    CtaApiRequestService(RestTemplate restTemplate, ObjectMapper objectMapper, CtaApiUriBuilder ctaApiUriBuilder) {
        this.restTemplate = restTemplate
        this.ctaApiUriBuilder = ctaApiUriBuilder
        this.objectMapper = objectMapper
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true).configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    BustimeResponse sendGetRequest(URI uri) {

        ResponseEntity<String> responseEntity
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)


        BustimeResponse bustimeResponse = objectMapper.readValue(responseEntity.getBody(), BustimeResponse)

        return bustimeResponse
    }

    List<Route> getRoutes() throws Exception {

        URI uri = ctaApiUriBuilder.buildRoutesUri()
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)

        BustimeResponse bustimeResponse = objectMapper.readValue(responseEntity.getBody(), BustimeResponse)
        log.debug(bustimeResponse.getRoutes().toString())

        reportErrors(bustimeResponse)
        return bustimeResponse.getRoutes()
    }

    List<Direction> getDirections(String routeId) throws Exception {

        URI uri = ctaApiUriBuilder.buildDirectionsUri(routeId)
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)

        BustimeResponse bustimeResponse = objectMapper.readValue(responseEntity.getBody(), BustimeResponse)
        log.debug(bustimeResponse.getDirections().toString())

        reportErrors(bustimeResponse)
        return bustimeResponse.getDirections()
    }

    List<Stop> getStops(String routeId, Direction direction) throws Exception {

        URI uri = ctaApiUriBuilder.buildStopsUri(routeId, direction)
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)

        BustimeResponse bustimeResponse = objectMapper.readValue(responseEntity.getBody(), BustimeResponse)
        log.debug(bustimeResponse.getStops().toString())

        reportErrors(bustimeResponse)
        return bustimeResponse.getStops()
    }

    List<Prediction> getPredictions(String routeId, String stopId, int resultsLimit = ctaApiUriBuilder.getDefaultPredictionLimit()) throws Exception {

        URI uri = ctaApiUriBuilder.buildPredictionsUri(routeId, stopId, resultsLimit)
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)

        BustimeResponse bustimeResponse = objectMapper.readValue(responseEntity.getBody(), BustimeResponse)
        log.debug(bustimeResponse.getPredictions().toString())

        reportErrors(bustimeResponse)
        return bustimeResponse.getPredictions()
    }

    private static void reportErrors(BustimeResponse response) throws Exception {

        if (response.hasErrors()) {

            String ctaErrorJson = JsonOutput.toJson(response.getErrors()).toString()
            log.error("Get request resulted in ${response.getErrors().size()} error(s)")
            log.error(ctaErrorJson)

            throw new Exception(ctaErrorJson)
        }
    }

    static enum CtaErrorMessageConstants {
        BAD_PARAM('No data found for parmeter'), NO_SERVICE_SCHEDULED('No service scheduled')

        String message

        CtaErrorMessageConstants(String message) {
            this.message = message
        }
    }
}
