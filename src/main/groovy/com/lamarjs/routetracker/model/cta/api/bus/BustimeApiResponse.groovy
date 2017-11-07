package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaApiResponse
import groovy.transform.ToString

@ToString(includeNames = true)
class BustimeApiResponse implements CtaApiResponse {

    @JsonProperty(value = "bustime-response")
    Map<String, Object> payload
    List<Map<String, String>> error

    @Override
    boolean hasError() {
        return error
    }

    @Override
    boolean hasPayload() {
        return payload
    }
}
