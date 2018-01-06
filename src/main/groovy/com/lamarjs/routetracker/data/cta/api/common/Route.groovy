package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import java.time.LocalDate

@Entity
@ToString(includeNames = true)
class Route implements Serializable {
    @Id
    @JsonProperty(value = "rt")
    String routeId
    @JsonProperty(value = "rtnm")
    String routeName
    @JsonProperty(value = "rtclr")
    String routeColor
    @JsonIgnore(value = false)
    Map<Direction, List<Stop>> stops
    @JsonIgnore(value = false)
    LocalDate createdDate

    @JsonIgnore(value = true)
    void setStops(Map<Direction, List<Stop>> stops) {
        this.stops = stops
    }

    @JsonIgnore(value = true)
    void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate
    }
}