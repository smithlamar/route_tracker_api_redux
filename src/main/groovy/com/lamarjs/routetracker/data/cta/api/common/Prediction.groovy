package com.lamarjs.routetracker.data.cta.api.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

@ToString(includeNames = true)
class Prediction {
    @JsonProperty(value = "tmstmp")
    String createdTimestamp;  // Ex: "20170314 11:25"
    @JsonProperty(value = "stpnm")
    String stopName;            // Ex: "Michigan \u0026 Balbo"
    @JsonProperty(value = "stpid")
    int stopId;                 // Ex: "1584"
    @JsonProperty(value = "rt")
    String routeId;             // Ex: "4"
    @JsonProperty(value = "rtdir")
    String lineDirection;      // Ex: "Northbound"
    @JsonProperty(value = "des")
    String finalDestination;    // Ex: "Illinois Center"
    @JsonProperty(value = "prdctdn")
    String eta;      // Ex: "20170314 11:32"
    @JsonProperty(value = "dly")
    boolean isDelayed;
    @JsonProperty(value = "prdtm")
    String etaMinutes; // Ex: "7"

    @Override
    public String toString() {
        return etaMinutes + "m";
    }
}
