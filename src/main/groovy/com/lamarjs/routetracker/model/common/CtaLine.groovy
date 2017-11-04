package com.lamarjs.routetracker.model.common

import org.springframework.stereotype.Component

class CtaLine {
    String lineId
    String lineName
    List<Route> routes;
    Direction direction
}
