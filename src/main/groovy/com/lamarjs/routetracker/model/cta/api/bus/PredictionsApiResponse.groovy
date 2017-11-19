package com.lamarjs.routetracker.model.cta.api.bus

import com.lamarjs.routetracker.model.cta.api.common.Prediction

class PredictionsApiResponse extends BustimeApiResponse<Prediction> {

    @Override
    List<Prediction> getPayloadTargetEntity() {
        return payload.get("predictions") as List<Prediction>
    }
}
