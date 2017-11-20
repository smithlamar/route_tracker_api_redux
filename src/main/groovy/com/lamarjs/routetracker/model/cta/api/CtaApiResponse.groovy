package com.lamarjs.routetracker.model.cta.api

interface CtaApiResponse {

    List<Map<String, String>> getErrors()

    List<String> getErrorMessages()

    boolean hasErrors()

}