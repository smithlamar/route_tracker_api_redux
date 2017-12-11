package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.model.cta.api.common.Route
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Slf4j
class CtaApiRequestService {

    RestTemplate restTemplate

    @Autowired
    CtaApiRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    ResponseEntity<BustimeResponse> sendGetRequest(URI uri) {

        ResponseEntity<BustimeResponse> responseEntity
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, BustimeResponse)

        return responseEntity
    }

    void reportErrors(BustimeResponse response) throws Exception {

        if (response.hasErrors()) {
            log.error("Get request resulted in ${response.getErrors().size()} error(s)")
            throw new Exception(response.getErrorMessages().toListString())
        }
    }
}
