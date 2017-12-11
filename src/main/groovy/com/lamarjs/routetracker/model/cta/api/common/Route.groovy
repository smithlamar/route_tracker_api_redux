package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import groovy.transform.ToString

@ToString(includeNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
class Route {
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName
    @JsonProperty(value = "rtclr")
    String routeColor
//    Map<Direction, List<Stop>> stops
}