package com.lamarjs.routetracker.util

import com.lamarjs.routetracker.data.cta.api.common.Direction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
//@ContextConfiguration(classes = [CtaApiConfig])
class CtaApiUriBuilderTest extends Specification {

    static final String TEST_ROUTE_CODE = 4
    static final Direction TEST_DIRECTION = new Direction(direction: "Northbound")
    static final String TEST_STOP_ID = 1584
    static final int TEST_RESULTS_LIMIT = 3

    @Autowired
    @Qualifier("busApiKey")
    String busApiKey

    @Autowired
    CtaApiUriBuilder builder

    def 'routesUriBuilder should match expectedUri'() {

        given: "expected URIs"
        URI expectedRoutesUri = new URI(
                "http://ctabustracker.com/bustime/api/v2/getroutes?key=${busApiKey}&format=json")

        when:
        URI routesUri = builder.buildRoutesUri()

        then:
        routesUri == expectedRoutesUri
    }

    def 'directionsUriBuilder should produce expectedUri'() {

        given: "expected URIs"
        URI expectedDirectionsUri = new URI(
                "http://ctabustracker.com/bustime/api/v2/getdirections?key=${busApiKey}&format=json&" +
                        "rt=${TEST_ROUTE_CODE}")

        when:
        URI directionsUri = builder.buildDirectionsUri(TEST_ROUTE_CODE)

        then:
        directionsUri == expectedDirectionsUri
    }

    def 'stopsUriBuilder should produce expectedUri'() {

        given: "expected URIs"
        URI expectedStopsUri = new URI(
                "http://ctabustracker.com/bustime/api/v2/getstops?key=${busApiKey}" +
                        "&format=json&rt=${TEST_ROUTE_CODE}&dir=${TEST_DIRECTION}")

        when:
        URI stopsUri = builder.buildStopsUri(TEST_ROUTE_CODE, TEST_DIRECTION)

        then:
        stopsUri == expectedStopsUri
    }

    def 'predictionsUriBuilder should produce expectedUri'() {

        given: "expected URIs"
        URI expectedPredictionsUri = new URI("http://ctabustracker.com/bustime/api/v2/getpredictions?key=${busApiKey}" +
                                                     "&format=json&rt=${TEST_ROUTE_CODE}&stpid=${TEST_STOP_ID}&top=" +
                                                     "${TEST_RESULTS_LIMIT}")

        when:
        URI predictionsUri = builder.buildPredictionsUri(TEST_ROUTE_CODE, TEST_STOP_ID, TEST_RESULTS_LIMIT)

        then:
        predictionsUri == expectedPredictionsUri
    }

}
