package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.BaseSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class BustimeResponseTest extends BaseSpecification {

    def "responseShouldBeInstantiatedFromMapper"() {


        String jsonString = jsonSampleFileMap.get("directions.json")


        BustimeResponse response = mapper.readValue(jsonString, BustimeResponse)

        expect:
        response
    }
}
