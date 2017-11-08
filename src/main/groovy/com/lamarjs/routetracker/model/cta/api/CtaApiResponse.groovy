package com.lamarjs.routetracker.model.cta.api

interface CtaApiResponse<T extends CtaEntity> {

    Map<String, Object> getPayload()

    List<T> getPayloadTargetEntity()

    List<Map<String, String>> getError()

    boolean hasError()

}