package com.lamarjs.routetracker.model.cta

import com.lamarjs.routetracker.model.cta.api.common.Direction
import com.lamarjs.routetracker.model.cta.api.common.Route

class CtaLine {
    String lineId
    String lineName
    List<Route> routes;
    Direction direction
}
