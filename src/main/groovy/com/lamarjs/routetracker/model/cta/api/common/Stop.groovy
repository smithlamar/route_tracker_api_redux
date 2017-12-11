package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

@ToString(includeNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
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
