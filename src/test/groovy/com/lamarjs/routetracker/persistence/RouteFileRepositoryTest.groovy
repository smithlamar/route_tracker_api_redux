package com.lamarjs.routetracker.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Route
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import spock.lang.Shared

import java.nio.file.Files
import java.nio.file.attribute.FileTime
import java.time.LocalDateTime
import java.time.ZoneOffset

@Slf4j
class RouteFileRepositoryTest extends BaseSpecification {

    @Shared
    String TEST_FILE_PATH = "./testRoutesFile.json"

    @Autowired
    @Qualifier("objectMapper") ObjectMapper objectMapper

    RouteFileRepository routeFileRepository

    void setup() {
        routeFileRepository = new RouteFileRepository(objectMapper, TEST_FILE_PATH);
    }

    void cleanup() {

        File testFile = new File(TEST_FILE_PATH)
        if (testFile.exists()) {
            testFile.delete()
            log.debug("Deleted test routes file.")
        }
    }

    def "LoadRoutesFromFileReturnsSavedFileWithCorrectValues"() {

        assert(objectMapper != null)
        routeFileRepository.saveRoutes(testRoutes)
        List<Route> loadedRoutes = routeFileRepository.getRoutes()

        expect:
        loadedRoutes.toString() == testRoutes.toString()
    }

    def "SaveRoutesToFileProducesFile"() {
        routeFileRepository.saveRoutes(testRoutes)
        File testFile = new File(TEST_FILE_PATH)

        expect:
        testFile.exists()
        testFile.length() > 0
    }

    def "testSavedRoutesFileIsStale"() {
        routeFileRepository.saveRoutes(testRoutes)
        File testFile = new File(TEST_FILE_PATH)
        long thirtySecsAsMilli = 1000L * 30

        when:
        Files.setLastModifiedTime(testFile.toPath(), FileTime.fromMillis(LocalDateTime.now().minusDays(8).toEpochSecond(ZoneOffset.UTC)))

        then:
        routeFileRepository.isStale()

        when:
        Files.setLastModifiedTime(testFile.toPath(), FileTime.fromMillis(LocalDateTime.now().minusDays(6).toEpochSecond(ZoneOffset.UTC)))

        then:
        !routeFileRepository.isStale()
    }
}
