package com.lamarjs.routetracker.util

import com.lamarjs.routetracker.model.common.Direction
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class CtaApiUriBuilder {

    private UriComponentsBuilder routesUriBuilder
    private UriComponentsBuilder directionsUriBuilder
    private UriComponentsBuilder stopsUriBuilder
    private UriComponentsBuilder predictionsUriBuilder

    CtaApiUriBuilder(
            UriComponentsBuilder routesUriBuilder, UriComponentsBuilder directionsUriBuilder,
            UriComponentsBuilder stopsUriBuilder, UriComponentsBuilder predictionsUriBuilder) {
        this.routesUriBuilder = routesUriBuilder
        this.directionsUriBuilder = directionsUriBuilder
        this.stopsUriBuilder = stopsUriBuilder
        this.predictionsUriBuilder = predictionsUriBuilder
    }

    static URI build(UriComponentsBuilder builder, List<Object> parameters) {
        builder.buildAndExpand(parameters.toArray()).encode().toUri()
    }

    URI buildRoutesUri() {
        return routesUriBuilder.build(true).toUri()
    }

    URI buildDirectionsUri(String routeCode) {
        return build(directionsUriBuilder, [routeCode])
    }

    URI buildStopsUri(String routeCode, Direction direction) {
        return build(stopsUriBuilder, [routeCode, direction])
    }

    URI buildPredictionsUri(String routeCode, String stopId, int resultsLimit) {
        return build(predictionsUriBuilder, [routeCode, stopId, resultsLimit])
    }
}
