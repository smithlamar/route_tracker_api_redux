package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaEntity

class Direction implements CtaEntity {

    static final String NORTHBOUND = "Northbound", SOUTHBOUND = "Southbound", EASTBOUND = "Eastbound",
                        WESTBOUND = "Westbound"

    @JsonProperty(value = "dir")
    String direction
}