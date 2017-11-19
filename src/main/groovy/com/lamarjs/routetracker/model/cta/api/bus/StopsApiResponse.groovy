package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.model.cta.api.common.Stop

class StopsApiResponse extends BustimeApiResponse<Stop> {

    @Override
    List<Stop> getPayloadTargetEntity() {
        return payload.get("stops") as List<Stop>
    }
}
