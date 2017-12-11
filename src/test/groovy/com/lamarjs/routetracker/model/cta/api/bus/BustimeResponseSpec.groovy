package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.BaseSpecification
import spock.lang.Unroll

@Unroll
class BustimeResponseSpec extends BaseSpecification {

    def "should succesfully deserialize #jsonSample"() {

        given:

        String jsonString = jsonSamplesAsStrings.get(jsonSample)

        and: "json sample mapped to BustimeApiResponseWrapper"
        BustimeResponse routesResponse = mapper.readerFor(BustimeResponse).readValue(jsonString)

        expect:
        routesResponse.getErrors() == jsonSamplesAsMaps.get("bustime-response")

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
        routesResponse.getBustimeResponse() == expectedDeserializedObject.get("bustime-response")

        where:
        jsonSampleFileUri            | hasError
        "directions.json"            | false
        "routes.json"                | false
        "error_invalid_key.json"     | true
        "error_bad_route_param.json" | true

    }


}
