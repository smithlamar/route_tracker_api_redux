package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.model.cta.api.common.Route

class RoutesApiResponse extends BustimeApiResponse<Route> {

    @Override
    List<Route> getPayloadTargetEntity() {
        return payload.get("routes") as List<Route>
    }
}
