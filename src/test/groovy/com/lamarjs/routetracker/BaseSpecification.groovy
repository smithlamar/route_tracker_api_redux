package com.lamarjs.routetracker

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE

@SpringBootTest
class BaseSpecification extends Specification {

    @Shared
    Map<String, String> jsonSampleFilesAsStrings
    @Shared
    Map<String, Map<String, Object>> jsonSamplesAsMaps

    void setupSpec() {

        List<File> jsonSampleUris = new File('src/test/resources/sampledata/bustimeapi/response/json/').listFiles().
                toList()

        jsonSampleFilesAsStrings = new HashMap<>()
        jsonSamplesAsMaps = new HashMap<>()
        JsonSlurper slurper = new JsonSlurper()

        jsonSampleUris.forEach({ file ->
            jsonSampleFilesAsStrings.put(file.getName(), file.getText())
            jsonSamplesAsMaps.put(file.getName(), slurper.parse(file) as Map<String, Object>)
        })
    }
}
