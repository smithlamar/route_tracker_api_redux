package com.lamarjs.routetracker.data.cta.api

import com.fasterxml.jackson.annotation.JsonProperty

trait CtaApiResponse {

    @JsonProperty(value = "error")
    List<Map<String, String>> errors

    List<String> getErrorMessages() {

        List<String> errorMessages = new ArrayList<>()

        getErrors().forEach({ error ->
            errorMessages.add(error.get("msg"))
        })

        return errorMessages
    }

    boolean hasErrors() {
        return errors
    }

}