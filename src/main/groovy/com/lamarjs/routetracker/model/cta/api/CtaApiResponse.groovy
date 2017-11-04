package com.lamarjs.routetracker.model.cta.api

interface CtaApiResponse<T> {
    List<Map<String, String>> getError()

    T getPayload()

    boolean hasError()

    boolean hasPayload()
}