package com.lamarjs.routetracker.model.common

import com.fasterxml.jackson.annotation.JsonProperty

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
