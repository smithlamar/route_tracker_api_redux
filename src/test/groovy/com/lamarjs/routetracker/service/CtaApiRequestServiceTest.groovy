package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
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

    private static final String TEST_ROUTE_ID_COTTAGE_GROVE = 4

    def "directionShouldBeInstantiatedFromRequestService"() {
        List<Direction> directions = requestService.getDirections(TEST_ROUTE_ID_COTTAGE_GROVE)

        expect:
        directions
        directions.get(0)
    }

    def "directionsShouldBeInstantiatedFromRequestService"() {
        URI uri = builder.buildRoutesUri()
        ResponseEntity<BustimeResponse> response = requestService.sendGetRequest(uri)

        expect:
        response.getBody().getRoutes()
    }
}
