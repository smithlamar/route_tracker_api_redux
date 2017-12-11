package com.lamarjs.routetracker.model.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty

class BustimeResponseWrapper {

    @JsonProperty(value = "bustime-response")
    BustimeResponse bustimeResponse
}
