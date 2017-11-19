package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.model.cta.api.common.Direction

class DirectionsApiResponse extends BustimeApiResponse<Direction> {

    @Override
    List<Direction> getPayloadTargetEntity() {
        return payload.get("directions") as List<Direction>
    }
}