package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.model.cta.api.CtaApiResponse
import groovy.transform.ToString

@ToString(includeNames = true)
class BustimeApiResponse<T> implements CtaApiResponse {
    List<Map<String, String>> error
    T payload

    @Override
    boolean hasError() {
        return error
    }

    @Override
    boolean hasPayload() {
        return payload
    }
}
