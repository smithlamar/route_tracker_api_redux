package com.lamarjs.routetracker.exception

class CtaApiException extends Exception{
    List<Map<String, String>> errors

    CtaApiException(List<Map<String, String>> errors, String message) {
        super(message)
        this.errors = errors
    }
}
