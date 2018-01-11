package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonProperty

class Direction {

    static final String NORTHBOUND = "Northbound", SOUTHBOUND = "Southbound", EASTBOUND = "Eastbound",
                        WESTBOUND = "Westbound"

    @JsonProperty(value = "dir")
    String direction

    @Override
    String toString() {
        return direction
    }
}