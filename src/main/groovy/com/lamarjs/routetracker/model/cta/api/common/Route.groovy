package com.lamarjs.routetracker.model.cta.api.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.lamarjs.routetracker.model.cta.api.CtaEntity
import groovy.transform.ToString

@ToString(includeNames = true)
class Route implements CtaEntity {
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName
    @JsonProperty(value = "rtclr")
    String routeColor
    Map<Direction, List<Stop>> stops
}