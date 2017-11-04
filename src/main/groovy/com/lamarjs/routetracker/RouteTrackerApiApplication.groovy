package com.lamarjs.routetracker

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@Slf4j
class RouteTrackerApiApplication {

    static void main(String[] args) {
        log.info("Starting RouteTracker API...")
        SpringApplication.run RouteTrackerApiApplication, args
    }
}
