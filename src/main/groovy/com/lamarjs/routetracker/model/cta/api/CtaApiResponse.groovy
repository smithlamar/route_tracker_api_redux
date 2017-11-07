package com.lamarjs.routetracker.model.cta.api

interface CtaApiResponse {

    List<Map<String, String>> getError()

    Map<String, Object> getPayload()

    boolean hasError()

    boolean hasPayload()

}