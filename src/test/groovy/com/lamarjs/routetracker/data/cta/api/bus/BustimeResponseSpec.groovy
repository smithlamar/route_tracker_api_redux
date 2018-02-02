package com.lamarjs.routetracker.data.cta.api.bus

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.BaseSpecification
import spock.lang.Unroll

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE

@Unroll
class BustimeResponseSpec extends BaseSpecification {

    ObjectMapper mapper = new ObjectMapper().configure(UNWRAP_ROOT_VALUE, true).configure(FAIL_ON_UNKNOWN_PROPERTIES, false)

    def "mapper should succesfully deserialize #jsonSample"() {

        given:

        String jsonString = jsonSamplesAsStrings.get(jsonSample)

        and: "json sample mapped to BustimeApiResponseWrapper"
        BustimeResponse routesResponse = mapper.readerFor(BustimeResponse).readValue(jsonString)

        expect:
        routesResponse

        where:
        jsonSample << jsonSamplesAsStrings.keySet().toList()
    }

    def "should deserialize #jsonSampleFileUri to have error indicator #hasError"() {
        given: "Sample json"
        String jsonString = jsonSamplesAsStrings.get(jsonSampleFileUri)
        Map<String, Object> expectedDeserializedObject = jsonSamplesAsMaps.get(jsonSampleFileUri)

        and: "json sample is mapped to bustime response"
        BustimeResponse routesResponse = mapper.readerFor(BustimeResponse).readValue(jsonString)

        expect:
        routesResponse.hasErrors() == hasError

        where:
        jsonSampleFileUri            | hasError
        "directions.json"            | false
        "routes.json"                | false
        "error_invalid_key.json"     | true
        "error_bad_route_param.json" | true

    }


}
