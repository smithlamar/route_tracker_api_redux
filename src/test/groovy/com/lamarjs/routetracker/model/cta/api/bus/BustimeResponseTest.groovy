package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.BaseSpecification

class BustimeResponseTest extends BaseSpecification {

    def "responseShouldBeInstantiatedFromMapper"() {


        String jsonString = jsonSampleFilesAsStrings.get("directions.json")


        BustimeResponse response = mapper.readValue(jsonString, BustimeResponse)

        expect:
        response
    }
}
