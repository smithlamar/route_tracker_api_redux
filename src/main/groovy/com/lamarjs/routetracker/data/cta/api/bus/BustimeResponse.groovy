package com.lamarjs.routetracker.data.cta.api.bus

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.lamarjs.routetracker.data.cta.api.CtaApiResponse
import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Prediction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import groovy.transform.ToString

@ToString
@JsonRootName(value = "bustime-response")
class BustimeResponse implements CtaApiResponse {
    List<Route> routes
    List<Direction> directions
    List<Stop> stops
    @JsonProperty(value = "prd")
    List<Prediction> predictions
}
