package com.lamarjs.routetracker.model.common

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

@ToString(includeNames = true)
class Route {
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName;
    @JsonProperty(value = "rtclr")
    String routeColor;
    List<Stop> stops;
}
