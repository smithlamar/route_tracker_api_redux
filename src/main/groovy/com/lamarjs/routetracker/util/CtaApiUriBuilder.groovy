package com.lamarjs.routetracker.util

import com.lamarjs.routetracker.data.cta.api.common.Direction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.util.UriComponentsBuilder

class CtaApiUriBuilder {

    private UriComponentsBuilder routesUriBuilder
    private UriComponentsBuilder directionsUriBuilder
    private UriComponentsBuilder stopsUriBuilder
    private UriComponentsBuilder predictionsUriBuilder
    int defaultPredictionLimit

    @Autowired
    CtaApiUriBuilder(
            UriComponentsBuilder routesUriBuilder, UriComponentsBuilder directionsUriBuilder,
            UriComponentsBuilder stopsUriBuilder, UriComponentsBuilder predictionsUriBuilder,
            int defaultPredictionLimit) {
        this.routesUriBuilder = routesUriBuilder
        this.directionsUriBuilder = directionsUriBuilder
        this.stopsUriBuilder = stopsUriBuilder
        this.predictionsUriBuilder = predictionsUriBuilder
        this.defaultPredictionLimit = defaultPredictionLimit
    }

    private static URI build(UriComponentsBuilder builder, List<Object> parameters) {
        builder.buildAndExpand(parameters.toArray()).encode().toUri()
    }

    URI buildRoutesUri() {
        return routesUriBuilder.build(true).toUri()
    }

    URI buildDirectionsUri(String routeId) {
        return build(directionsUriBuilder, [routeId])
    }

    URI buildStopsUri(String routeId, Direction direction) {
        return build(stopsUriBuilder, [routeId, direction])
    }

    URI buildPredictionsUri(String routeId, String stopId, int resultsLimit) {
        return build(predictionsUriBuilder, [routeId, stopId, resultsLimit])
    }

    URI buildPredictionsUri(String routeId, String stopId) {
        return buildPredictionsUri(routeId, stopId, defaultPredictionLimit)
    }
}
