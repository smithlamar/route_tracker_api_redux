package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity

@Embeddable
@ToString(includeNames = true)
class Stop {
    @JsonProperty(value = "stpid")
    int stopId;
    @JsonProperty(value = "stpnm")
    String stopName;
    @JsonProperty(value = "lat")
    double latitude;
    @JsonProperty(value = "lon")
    double longitude;
    @JsonIgnore(value = false)
    @Embedded
    Direction direction

    @JsonIgnore(value = true) // ignored for deserialization
    void setDirection(Direction direction) {
        this.direction = direction
    }
}
