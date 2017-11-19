package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaApiResponse
import com.lamarjs.routetracker.model.cta.api.CtaEntity
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Prediction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop
import groovy.transform.ToString

@ToString(includeNames = true)
abstract class BustimeApiResponse<T extends CtaEntity> implements CtaApiResponse {

    @JsonProperty(value = "bustime-response")
    Map<String, Object> payload

    @Override
    List<Map<String, String>> getErrors() {
        return payload.get("error") as List<Map<String, String>>
    }

    @Override
    List<String> getErrorMessages() {

        List<String> errorMessages = new ArrayList<>()

        getErrors().forEach({error ->
            errorMessages.add(error.get("msg"))
        })

        return errorMessages
    }

    @Override
    boolean hasErrors() {
        return payload?.containsKey("error")
    }
}