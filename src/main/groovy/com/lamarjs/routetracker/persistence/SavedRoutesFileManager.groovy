package com.lamarjs.routetracker.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.lamarjs.routetracker.data.cta.api.common.Route
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.nio.file.Files
import java.time.LocalDate

@Slf4j
class SavedRoutesFileManager {
    public static final String SAVED_ROUTES_ROOT_NAME = "routes"
    ObjectMapper objectMapper
    String routesJsonFilePath

    @Autowired
    SavedRoutesFileManager(ObjectMapper objectMapper, String routesJsonFilePath) {
        this.objectMapper = objectMapper
        this.routesJsonFilePath = routesJsonFilePath
    }

    List<Route> loadRoutes() {
        loadRoutesFromFile(routesJsonFilePath)
    }


    void saveRoutes(List<Route> routes) {
        saveRoutesToFile(routes, routesJsonFilePath)
    }

    List<Route> loadRoutesFromFile(String pathToJsonFile) {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Route>>() {
        }).withRootName(SAVED_ROUTES_ROOT_NAME)
        return reader.readValue(new File(pathToJsonFile))
    }

    void saveRoutesToFile(List<Route> routes, String path) {
        File outputFile = new File(path)
        File parentDirectory = new File(outputFile.getParent())
        parentDirectory.mkdir()

        File tempFile = new File(parentDirectory.toString() + "/temp_" + outputFile.getName())
        tempFile.createNewFile()

        ObjectWriter writer = objectMapper.writerFor(new TypeReference<List<Route>>() {
        }).with(SerializationFeature.WRAP_ROOT_VALUE).withRootName(SAVED_ROUTES_ROOT_NAME)

        tempFile << writer.writeValueAsString(routes)

        if (outputFile.exists()) {
            outputFile.delete()
        }

        tempFile.renameTo(outputFile)
        log.info("Wrote routes to file: ${outputFile}")
    }

    boolean savedRoutesFileIsStale() {
        return fileIsStale(routesJsonFilePath)
    }

    static boolean fileIsStale(String path) {
        File file = new File(path)
        return !file.exists() || file.lastModified() < LocalDate.now().minusDays(7).toEpochDay()
    }
}
