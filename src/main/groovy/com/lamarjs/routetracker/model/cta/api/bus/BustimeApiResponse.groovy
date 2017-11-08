package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaApiResponse
import com.lamarjs.routetracker.model.cta.api.CtaEntity
import groovy.transform.ToString

@ToString(includeNames = true)
class BustimeApiResponse<T extends CtaEntity> implements CtaApiResponse {

    @JsonProperty(value = "bustime-response")
    Map<String, Object> payload

    @Override
    List<T> getPayloadTargetEntity() {
        return payload.get("${T.simpleName}s") as List<T>
    }

    @Override
    List<Map<String, String>> getError() {
        return payload.get("error") as List<Map<String, String>>
    }

    @Override
    boolean hasError() {
        return payload?.containsKey("error")
    }

}
