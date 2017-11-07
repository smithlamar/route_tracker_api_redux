package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.bus.BustimeApiResponse
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import groovy.util.logging.Slf4j
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@Slf4j
class CtaApiRequestService {

    CtaApiUriBuilder uriBuilder

    CtaApiRequestService(CtaApiUriBuilder uriBuilder) {
        this.uriBuilder = uriBuilder
    }

    BustimeApiResponse requestRoutes() {
        RestTemplate restTemplate = new RestTemplate()
        URI url = uriBuilder.buildRoutesUri()

        ResponseEntity<BustimeApiResponse> response = restTemplate.
                exchange(url, HttpMethod.GET, null, BustimeApiResponse)
        log.info("Bustime request returned status code: ${response.getStatusCodeValue()}")

        return response.getBody()
    }
}
