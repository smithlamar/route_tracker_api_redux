package com.lamarjs.routetracker.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.data.cta.api.common.Route
import org.springframework.beans.factory.annotation.Autowired

class SavedRoutesFileManager {

    ObjectMapper objectMapper
    String routesJsonFilePath

    @Autowired
    SavedRoutesFileManager(ObjectMapper objectMapper, String routesJsonFilePath) {
        this.objectMapper = objectMapper
        this.routesJsonFilePath = routesJsonFilePath
    }

    static List<Route> loadRoutes() {}

    static void saveRoutes(List<Route> routes) {}

    static List<Route> loadRoutesFromFile(String path) {
        null
    }

    static void saveRoutesToFile(List<Route> routes, String path) {}

    static boolean savedRoutesFileIsStale(String path) {
        return true
    }
}
