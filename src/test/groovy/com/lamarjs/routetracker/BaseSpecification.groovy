package com.lamarjs.routetracker

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.config.CtaApiConfig
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE

@ContextConfiguration(classes = [CtaApiConfig])
class BaseSpecification extends Specification {

    final static String TEST_ROUTE_ID = 4
    final static Direction TEST_DIRECTION = new Direction()
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

    @Autowired
    CtaApiUriBuilder uriBuilder

    void setupSpec() {

        List<File> jsonSampleUris = new File('src/test/resources/sampledata/bustimeapi/response/json/').listFiles().
                toList()

        jsonSampleUris.forEach({ file ->
            jsonSampleFileMap.put(file.getName(), file.getText())
            slurpedJson.put(file.getName(), slurper.parse(file) as Map<String, Object>)
        })

        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES, UNWRAP_ROOT_VALUE)

        MockRestServiceServer
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }
}
