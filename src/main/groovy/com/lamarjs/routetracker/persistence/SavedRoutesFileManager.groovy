package com.lamarjs.routetracker.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.lamarjs.routetracker.data.cta.api.common.Route
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
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

    void saveRoutesToFile(List<Route> routes, String path) {
        File output = new File(path)
        output.createNewFile()

        ObjectWriter writer = objectMapper.writerFor(new TypeReference<List<Route>>() {
        }).with(SerializationFeature.WRAP_ROOT_VALUE).withRootName("routes")


        output << writer.writeValueAsString(routes)
        log.info("Wrote routes to file: ${output}")
    }

    static boolean savedRoutesFileIsStale(String path) {
        return true
    }
}
