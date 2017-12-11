package com.lamarjs.routetracker.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@Slf4j
class CtaApiRequestService {

    RestTemplate restTemplate
    ObjectMapper objectMapper
    CtaApiUriBuilder ctaApiUriBuilder

    @Autowired
    CtaApiRequestService(RestTemplate restTemplate, ObjectMapper objectMapper, CtaApiUriBuilder ctaApiUriBuilder) {
        this.restTemplate = restTemplate
        this.objectMapper = objectMapper
        this.ctaApiUriBuilder = ctaApiUriBuilder
    }

    BustimeResponse sendGetRequest(URI uri) {

        ResponseEntity<String> responseEntity
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)

        println(responseEntity.getBody())

        BustimeResponse bustimeResponse = objectMapper.readValue(responseEntity.getBody(), BustimeResponse)

        return bustimeResponse
    }

    List<Direction> getDirections(String routeId) {

        URI uri = ctaApiUriBuilder.buildDirectionsUri(routeId)

        ResponseEntity<String> responseEntity
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String)

        println(responseEntity.getBody())

        TypeReference typeRef = new TypeReference<Map<String, Map<String, List<Direction>>>>() {}

        Map<String, Map<String, List<Direction>>> body = objectMapper.readValue(responseEntity.getBody(), typeRef)

        return body.get("bustime-response").get("directions")
    }

    void reportErrors(BustimeResponse response) throws Exception {

        if (response.hasErrors()) {
            log.error("Get request resulted in ${response.getErrors().size()} error(s)")
            throw new Exception(response.getErrorMessages().toListString())
        }
    }
}
