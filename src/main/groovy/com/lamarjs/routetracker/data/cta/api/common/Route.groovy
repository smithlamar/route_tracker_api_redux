package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

import java.time.LocalDate

@ToString(includeNames = true)
class Route {
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName
    @JsonProperty(value = "rtclr")
    String routeColor
    List<Stop> stops
    LocalDate createdDate

//    @JsonIgnore(value = true) // Ignored for deserialization
//    void setStops(List<Stop> stops) {
//        this.stops = stops
//    }
//
//    @JsonIgnore(value = true)
//    void setCreatedDate(LocalDate createdDate) {
//        this.createdDate = createdDate
//    }
}