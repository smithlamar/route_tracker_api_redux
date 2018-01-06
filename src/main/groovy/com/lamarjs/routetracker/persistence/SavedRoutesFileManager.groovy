package com.lamarjs.routetracker.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.data.cta.api.common.Route
import org.springframework.beans.factory.annotation.Autowired

import java.nio.file.Path

class SavedRoutesFileManager {

    ObjectMapper objectMapper
    String routesFilePath

    @Autowired
    SavedRoutesFileManager(ObjectMapper objectMapper, String routesFilePath) {
        this.objectMapper = objectMapper
        this.routesFilePath = routesFilePath
    }

    static List<Route> loadRoutesFromFile(String path) {
        null
    }

    static public void saveRoutesToFile(List<Route> routes, String path) {}

    static public boolean savedRoutesFileIsStale(String path) {
        return true
    }
}
