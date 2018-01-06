package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

@ToString(includeNames = true)
class Stop {
    @JsonProperty(value = "stpid")
    int stopId;
    @JsonProperty(value = "stpnm")
    String stopName;
    @JsonProperty(value = "lat")
    double latitude;
    @JsonProperty(value = "lon")
    double longitude;
}
