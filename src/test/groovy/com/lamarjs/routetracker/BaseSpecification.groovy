package com.lamarjs.routetracker

import com.fasterxml.jackson.databind.ObjectMapper
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

    @Shared
    Map<String, String> jsonSamplesAsStrings = new HashMap<>()
    @Shared
    Map<String, Map<String, Object>> jsonSamplesAsMaps = new HashMap<>()
    @Shared
    ObjectMapper mapper = new ObjectMapper()

    void setupSpec() {

        List<File> jsonSampleUris = new File('src/test/resources/sampledata/bustimeapi/response/json/').listFiles().
                toList()

        JsonSlurper slurper = new JsonSlurper()
        jsonSampleUris.forEach({ file ->
            jsonSamplesAsStrings.put(file.getName(), file.getText())
            jsonSamplesAsMaps.put(file.getName(), slurper.parse(file) as Map<String, Object>)
        })

        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(UNWRAP_ROOT_VALUE, true)
    }
}
