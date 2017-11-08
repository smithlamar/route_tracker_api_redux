package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.config.CtaApiConfig
import com.lamarjs.routetracker.model.cta.api.bus.BustimeApiResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Prediction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Unroll

import static org.springframework.test.web.client.ExpectedCount.manyTimes
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@ContextConfiguration(classes = [CtaApiConfig])
@Unroll
class CtaApiRequestServiceTest extends BaseSpecification {

    final static String TEST_ROUTE_CODE = 4
    final static Direction TEST_DIRECTION = Direction.NORTHBOUND
    final static String TEST_STOP_ID = 1584

    @Shared
    RestTemplate restTemplate = new RestTemplate()

    @Autowired
    CtaApiUriBuilder uriBuilder


    @Shared
    CtaApiRequestService requestService = new CtaApiRequestService(restTemplate)
    @Shared
    MockRestServiceServer server

    void setupSpec() {
        MockRestServiceServer
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }


    def "should deserialize jsonSample from uri for type with correct response"() {

        setup: "a mock server returning #jsonSample"

        server.expect(manyTimes(), method(HttpMethod.GET)).
                andRespond(withSuccess(jsonSampleFileMap.get("routes.json"), MediaType.APPLICATION_JSON))

        and: "a set of cta api URIs"

        URI routesUri = uriBuilder.buildRoutesUri()
        URI directionsUri = uriBuilder.buildDirectionsUri(TEST_ROUTE_CODE)
        URI stopsUri = uriBuilder.buildStopsUri(TEST_ROUTE_CODE, TEST_DIRECTION)
        URI predictionsUri = uriBuilder.buildPredictionsUri(TEST_ROUTE_CODE, TEST_STOP_ID)

        and: "a set of parametized type references"

        ParameterizedTypeReference<BustimeApiResponse<Route>> routeType
        routeType = new ParameterizedTypeReference<BustimeApiResponse<Route>>() {}

        ParameterizedTypeReference<BustimeApiResponse<Direction>> directionType
        directionType = new ParameterizedTypeReference<BustimeApiResponse<Direction>>() {}

        ParameterizedTypeReference<BustimeApiResponse<Stop>> stopType
        stopType = new ParameterizedTypeReference<BustimeApiResponse<Stop>>() {}

        ParameterizedTypeReference<BustimeApiResponse<Prediction>> predictionType
        predictionType = new ParameterizedTypeReference<BustimeApiResponse<Prediction>>() {}

        and: "request service under test returning response body"
        BustimeApiResponse<Route> routesResponseBody = requestService.sendGet(routesUri, routeType)
        List<Direction> directionsResponseBody = requestService.sendGet(directionsUri, directionType).
                getPayloadTargetEntity()
        List<Stop> stopsResponseBody = requestService.sendGet(stopsUri, stopType).getPayloadTargetEntity()
        List<Prediction> predictionResponseBody = requestService.sendGet(predictionsUri, predictionType).
                getPayloadTargetEntity()

        expect:
        routesResponseBody.getPayload() == slurpedJson.get("routes.json").get("bustime-response")
        // TODO: clean up test and add expect conditions for other responses


    }

}
