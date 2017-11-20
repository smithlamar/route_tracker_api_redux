package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.bus.BustimeApiResponseWrapper
import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
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

    BustimeResponse sendGetRequest(URI uri) {
        ResponseEntity<BustimeApiResponseWrapper> responseEntity = restTemplate.
                exchange(uri, HttpMethod.GET, null, BustimeApiResponseWrapper)

        log.info("CTA Bustime API request returned status code: ${responseEntity.getStatusCodeValue()}")

        BustimeResponse response = responseEntity.getBody().getBustimeResponse()
        reportErrors(response)

        return response

    }

    void reportErrors(BustimeResponse response) throws Exception {

        if (response.hasErrors()) {
            log.error("Get request resulted in ${response.getErrors().size()} error(s)")
            throw new Exception(response.getErrorMessages().toListString())
        }
    }
}
