package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
class CtaApiRequestServiceTest extends BaseSpecification {

    @Autowired
    CtaApiUriBuilder builder
    @Autowired
    CtaApiRequestService requestService

    def "bustimeResponseShouldBeInstantiatedFromRequestService"() {
        URI uri = builder.buildDirectionsUri("4")
        ResponseEntity<BustimeResponse> response = requestService.sendGetRequest(uri)

        expect:
        response
    }

    def "directionsShouldBeInstantiatedFromRequestService"() {
        URI uri = builder.buildDirectionsUri("4")
        ResponseEntity<BustimeResponse> response = requestService.sendGetRequest(uri)

        expect:
        response.getBody().getDirections()
    }
}
