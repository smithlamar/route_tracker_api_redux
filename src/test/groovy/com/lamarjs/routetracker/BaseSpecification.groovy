package com.lamarjs.routetracker

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.service.CtaApiRequestService
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE

@SpringBootTest
class BaseSpecification extends Specification {

    final static String TEST_ROUTE_ID = 4
    final static Direction TEST_DIRECTION = new Direction(direction: "Northbound")
    final static String TEST_STOP_ID = 1584
    final static String TEST_ROUTE_COLOR = "cc3300"
    final static String TEST_ROUTE_NAME = "Cottage Grove"

    @Shared
    JsonSlurper slurper = new JsonSlurper()
    @Shared
    Map<String, String> jsonSampleFileMap = new HashMap<>()
    @Shared
    Map<String, Map<String, Object>> slurpedJson = new HashMap<>()
    @Shared
    ObjectMapper mapper = new ObjectMapper()
    @Shared
    RestTemplate restTemplate = new RestTemplate()
    @Shared
    MockRestServiceServer server
    @Shared
    CtaApiRequestService requestService = new CtaApiRequestService(restTemplate)

    void setupSpec() {

        List<File> jsonSampleUris = new File('src/test/resources/sampledata/bustimeapi/response/json/').listFiles().
                toList()

        jsonSampleUris.forEach({ file ->
            jsonSampleFileMap.put(file.getName(), file.getText())
            slurpedJson.put(file.getName(), slurper.parse(file) as Map<String, Object>)
        })

        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(UNWRAP_ROOT_VALUE, false)

        MockRestServiceServer
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }
}
