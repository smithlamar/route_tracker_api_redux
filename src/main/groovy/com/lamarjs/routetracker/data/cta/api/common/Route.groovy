package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

import java.time.LocalDateTime

@ToString(includeNames = true)
class Route {
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName
    @JsonProperty(value = "rtclr")
    String routeColor
    List<Stop> stops
    Long createdDateInEpochSeconds
}