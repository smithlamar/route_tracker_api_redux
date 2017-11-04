package com.lamarjs.routetracker.model

import com.lamarjs.routetracker.model.cta.api.bus.BustimeApiResponse
import spock.lang.Specification
import spock.lang.Unroll

class BustimeApiResponseTest extends Specification {

    static List<Map<String, String>> presentErrorList = [["msg": "error message"]]
    static List<Map<String, String>> emptyErrorList = new ArrayList<>()
    static List<Map<String, String>> nullErrorList

    static String presentPayload = "payload: some payload."
    static String nullPayload

    @Unroll
    def "returns #hasError for error #givenError"() {

        given: "A response returning givenError"
        BustimeApiResponse<String> response = new BustimeApiResponse<>()
        response.setError(givenError)

        expect:
        response.hasError() == hasError

        where:
        givenError       || hasError
        presentErrorList || true
        emptyErrorList   || false
        nullErrorList    || false
    }

    @Unroll
    def "returns #hasPayload for payload #givenPayload"() {

        given: "A response returning givenPayload"
        BustimeApiResponse<String> response = new BustimeApiResponse<>()
        response.setPayload(givenPayload)

        expect:
        response.hasPayload() == hasPayload

        where:
        givenPayload   || hasPayload
        presentPayload || true
        nullPayload    || false
    }


}
