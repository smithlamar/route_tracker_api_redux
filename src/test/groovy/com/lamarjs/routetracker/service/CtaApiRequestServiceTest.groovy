package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Prediction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop
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
    private static final String TEST_ROUTE_ID_BAD_ROUTE = 0
    private static final Direction TEST_DIRECTION_NORTHBOUND = new Direction(direction: Direction.NORTHBOUND)
    private static final String TEST_STOP_ID_35TH_ST = "7629"
    private static final int TEST_RESULTS_LIMIT = 1

    def "routes should be instantiated from request Service"() {
        given:
        List<Route> routes = requestService.getRoutes()

        expect:
        routes.size() > 0
    }

    def "directions should be instantiated from request service"() {
        given:
        List<Direction> directions = requestService.getDirections(TEST_ROUTE_ID_COTTAGE_GROVE)

        expect:
        directions.size() > 0
    }

    def "stops should be instantiated from request service"() {
        given:
        List<Stop> stops = requestService.getStops(TEST_ROUTE_ID_COTTAGE_GROVE, TEST_DIRECTION_NORTHBOUND)

        expect:
        stops.size() > 0
    }

    def "predictions should be instantiated from request service using default results limit"() {
        given:
        List<Prediction> predictions = requestService.getPredictions(TEST_ROUTE_ID_COTTAGE_GROVE, TEST_STOP_ID_35TH_ST)

        expect:
        predictions.size() > 0
    }

    def "predictions should be instantiated from request service using given results limit"() {
        given:
        List<Prediction> predictions = requestService.getPredictions(TEST_ROUTE_ID_COTTAGE_GROVE, TEST_STOP_ID_35TH_ST, TEST_RESULTS_LIMIT)

        expect:
        predictions.size() > 0
        predictions.size() < 2
    }

    def "errors should be instantiated from request service given bad paramater"() {

        given:
        Exception e

        when:

        try {
            List<Direction> directions = requestService.getDirections(TEST_ROUTE_ID_BAD_ROUTE)
        }
        catch (Exception error) {
            e = error
        }

        then:
        e
        e.getMessage()
    }
}
