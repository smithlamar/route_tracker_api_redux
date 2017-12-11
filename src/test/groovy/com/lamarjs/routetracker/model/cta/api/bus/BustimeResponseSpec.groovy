package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.BaseSpecification
import org.junit.Assert
import spock.lang.Unroll

@Unroll
class BustimeResponseSpec extends BaseSpecification {

    def "mapper should succesfully deserialize #jsonSample"() {

        given:

        String jsonString = jsonSampleFilesAsStrings.get(jsonSample)

        and: "json sample mapped to BustimeApiResponseWrapper"
        BustimeResponse routesResponse = mapper.readerFor(BustimeResponse).readValue(jsonString)

        expect:
        routesResponse

        where:
        jsonSample << jsonSampleFilesAsStrings.keySet().toList()
    }

    def "should deserialize #jsonSampleFileUri to have error indicator #hasError"() {
        given: "Sample json"
        String jsonString = jsonSampleFilesAsStrings.get(jsonSampleFileUri)
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
