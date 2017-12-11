package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

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