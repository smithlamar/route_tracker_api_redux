package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

@ToString(includeNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
class Direction {

//    static final String NORTHBOUND = "Northbound", SOUTHBOUND = "Southbound", EASTBOUND = "Eastbound",
//                        WESTBOUND = "Westbound"

    @JsonProperty(value = "dir")
    String direction
}