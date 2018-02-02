package com.lamarjs.routetracker.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.lamarjs.routetracker.data.cta.api.common.Route
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime
import java.time.ZoneOffset

@Slf4j
class RouteFileRepository implements RouteRepository {

    public static final String ROUTES_JSON_ROOT_NAME = "routes"
    ObjectMapper objectMapper
    String routesJsonFilePath

    RouteFileRepository(ObjectMapper objectMapper, String routesJsonFilePath) {
        this.objectMapper = objectMapper
        this.routesJsonFilePath = routesJsonFilePath
    }

    @Override
    List<Route> getRoutes() {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Route>>() {
        }).withRootName(ROUTES_JSON_ROOT_NAME)
        return reader.readValue(new File(routesJsonFilePath))
    }

    @Override
    void saveRoutes(List<Route> routes) {
        File outputFile = new File(routesJsonFilePath)
        File parentDirectory = new File(outputFile.getParent())
        parentDirectory.mkdir()

        File tempFile = new File(parentDirectory.toString() + "/temp_" + outputFile.getName())
        tempFile.createNewFile()
        ObjectWriter writer = objectMapper.writerFor(new TypeReference<List<Route>>() {
        }).with(SerializationFeature.WRAP_ROOT_VALUE).withRootName(ROUTES_JSON_ROOT_NAME)

        tempFile << writer.writeValueAsString(routes)

        if (outputFile.exists()) {
            outputFile.delete()
        }

        tempFile.renameTo(outputFile)
        outputFile.setLastModified(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        log.info("Wrote routes to file: ${outputFile}")
    }

    @Override
    void deleteRoutes() {
        File savedRoutes = new File(routesJsonFilePath)
        if (savedRoutes.exists()) {
            savedRoutes.delete()
        }
        log.info("Saved routes file: ${routesJsonFilePath} deleted.")
    }

    @Override
    boolean isStale() {
        File file = new File(routesJsonFilePath)
        return !file.exists() || PersistenceUtils.isOlderThanSevenDays(file.lastModified())
    }


}
