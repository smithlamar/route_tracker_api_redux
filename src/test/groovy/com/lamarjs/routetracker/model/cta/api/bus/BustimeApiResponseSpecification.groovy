package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.BaseSpecification
import spock.lang.Unroll

@Unroll
class BustimeApiResponseSpecification extends BaseSpecification {

    def "should succesfully deserialize #jsonSample"() {

        given:

        String jsonString = jsonSampleFileMap.get(jsonSample)
        Map<String, Object> slurpedSample = slurpedJson.get(jsonSample)

        and: "json sample mapped to BustimeApiResponse"
        BustimeApiResponse routesResponse = mapper.readerFor(BustimeApiResponse).readValue(jsonString)

        expect:
        routesResponse.getPayload() == slurpedSample.get("bustime-response")

        where:
        jsonSample << jsonSampleFileMap.keySet().toList()
    }

    def "should deserialize #jsonSampleFileUri to have error indicator #hasError"() {
        given: "Sample json"
        String jsonString = jsonSampleFileMap.get(jsonSampleFileUri)
        Map<String, Object> expectedDeserializedObject = slurpedJson.get(jsonSampleFileUri)

        and: "json sample is mapped to bustime response"
        BustimeApiResponse routesResponse = mapper.readerFor(BustimeApiResponse).readValue(jsonString)

        expect:
        routesResponse.getPayload() == expectedDeserializedObject.get("bustime-response")

        where:
        jsonSampleFileUri            | hasError
        "directions.json"            | false
        "routes.json"                | false
        "error_invalid_key.json"     | true
        "error_bad_route_param.json" | true

    }


}
