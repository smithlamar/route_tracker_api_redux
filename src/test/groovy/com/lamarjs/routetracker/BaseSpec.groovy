package com.lamarjs.routetracker

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import spock.lang.Shared
import spock.lang.Specification

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE

class BaseSpec extends Specification {

    @Shared
    JsonSlurper slurper = new JsonSlurper()
    @Shared
    Map<String, String> jsonSampleFileMap = new HashMap<>()
    @Shared
    Map<String, Map<String, Object>> slurpedJson = new HashMap<>()
    @Shared
    ObjectMapper mapper = new ObjectMapper()

    void setupSpec() {

        List<File> jsonSampleUris = new File('src/test/resources/sampledata/bustimeapi/response/json/').listFiles().
                toList()

        jsonSampleUris.forEach({ file ->
            jsonSampleFileMap.put(file.getName(), file.getText())
            slurpedJson.put(file.getName(), slurper.parse(file) as Map<String, Object>)
        })

        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES, UNWRAP_ROOT_VALUE)
    }
}
