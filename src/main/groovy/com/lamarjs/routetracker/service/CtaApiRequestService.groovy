package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.model.cta.api.CtaEntity
import com.lamarjs.routetracker.model.cta.api.bus.BustimeApiResponse
import groovy.util.logging.Slf4j
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Slf4j
@Service
class CtaApiRequestService {

    RestTemplate restTemplate

    CtaApiRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    public <T extends CtaEntity> BustimeApiResponse<T> sendGet(URI uri,
            ParameterizedTypeReference<BustimeApiResponse<T>> responseTypeReference) {

        ResponseEntity<BustimeApiResponse<T>> responseEntity = restTemplate.
                exchange(uri, HttpMethod.GET, null, responseTypeReference)
        log.info("Bus-time request returned status code: ${responseEntity.getStatusCodeValue()}")

        return responseEntity.getBody()
    }
}
