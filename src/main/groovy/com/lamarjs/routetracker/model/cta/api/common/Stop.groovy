package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaEntity

class Stop implements CtaEntity {
    @JsonProperty(value = "stpid")
    int stopId;
    @JsonProperty(value = "stpnm")
    String stopName;
    @JsonProperty(value = "lat")
    double latitude;
    @JsonProperty(value = "lon")
    double longitude;
}
