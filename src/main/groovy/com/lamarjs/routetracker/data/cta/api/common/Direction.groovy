package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
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