package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.BaseSpecification
import spock.lang.Unroll

@Unroll
class BustimeApiResponseSpec extends BaseSpecification {

    def "should succesfully deserialize #jsonSample"() {

        given:

        String jsonString = jsonSampleFileMap.get(jsonSample)
        BustimeResponse slurpedSample = slurpedJson.get(jsonSample)

        and: "json sample mapped to BustimeApiResponseWrapper"
        BustimeApiResponseWrapper routesResponse = mapper.readerFor(BustimeApiResponseWrapper).readValue(jsonString)

        expect:
        routesResponse.getBustimeResponse().getErrors() == slurpedSample.get("bustime-response")

        where:
        jsonSample << jsonSampleFileMap.keySet().toList()
    }

    def "should deserialize #jsonSampleFileUri to have error indicator #hasError"() {
        given: "Sample json"
        String jsonString = jsonSampleFileMap.get(jsonSampleFileUri)
        Map<String, Object> expectedDeserializedObject = slurpedJson.get(jsonSampleFileUri)

        and: "json sample is mapped to bustime response"
        BustimeApiResponseWrapper routesResponse = mapper.readerFor(BustimeApiResponseWrapper).readValue(jsonString)

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
