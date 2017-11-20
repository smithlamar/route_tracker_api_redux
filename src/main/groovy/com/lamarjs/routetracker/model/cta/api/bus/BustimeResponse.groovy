package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaApiResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Prediction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop

class BustimeResponse implements CtaApiResponse {

    @JsonProperty(value = "error")
    List<Map<String, String>> errors
    List<Route> routes
    List<Direction> directions
    List<Stop> stops
    List<Prediction> predictions

    @Override
    List<String> getErrorMessages() {

        List<String> errorMessages = new ArrayList<>()

        getErrors().forEach({ error ->
            errorMessages.add(error.get("msg"))
        })

        return errorMessages
    }

    @Override
    boolean hasErrors() {
        return getErrors()
    }
}
