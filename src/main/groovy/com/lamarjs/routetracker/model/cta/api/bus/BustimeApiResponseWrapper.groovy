package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty

class BustimeApiResponseWrapper {

    @JsonProperty(value = "bustime-response")
    BustimeResponse bustimeResponse
}