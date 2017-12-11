package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import groovy.transform.ToString

@ToString(includeNames = true)
class Route {
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName
    @JsonProperty(value = "rtclr")
    String routeColor
    @JsonIgnore(value = false)
    Map<Direction, List<Stop>> stops

    @JsonIgnore(value = true)
    void setStops(Map<Direction, List<Stop>> stops) {
        this.stops = stops
    }
}