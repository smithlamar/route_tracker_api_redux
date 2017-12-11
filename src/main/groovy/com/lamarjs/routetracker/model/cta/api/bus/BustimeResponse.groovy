package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.model.cta.api.CtaApiResponse
import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Prediction
import com.lamarjs.routetracker.model.cta.api.common.Route
import com.lamarjs.routetracker.model.cta.api.common.Stop

class BustimeResponse implements CtaApiResponse {

    List<Route> routes
    List<Direction> directions
    List<Stop> stops
    List<Prediction> predictions
}
