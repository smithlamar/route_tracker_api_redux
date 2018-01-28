package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Route
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import java.nio.file.Files
import java.nio.file.attribute.FileTime
import java.time.LocalDateTime
import java.time.ZoneOffset

class RouteFileRepositoryTest extends BaseSpecification {

    @Shared
    String testFilePath = "./testRoutesFile.json"

    @Autowired
    RouteFileRepository savedRoutesFileManager

    void setup() {
    }

    void cleanup() {
        new File(testFilePath).delete()
    }

    def "LoadRoutesFromFileReturnsSavedFileWithCorrectValues"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, testFilePath)
        List<Route> loadedRoutes = savedRoutesFileManager.loadRoutesFromFile(testFilePath)

        expect:
        loadedRoutes.toString() == testRoutes.toString()
    }

    def "SaveRoutesToFileProducesFile"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, testFilePath)
        File testFile = new File(testFilePath)

        expect:
        testFile.exists()
        testFile.length() > 0
    }

    def "testSavedRoutesFileIsStale"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, testFilePath)
        File testFile = new File(testFilePath)
        long thirtySecsAsMilli = 1000L * 30

        when:
        Files.setLastModifiedTime(testFile.toPath(), FileTime.fromMillis(LocalDateTime.now().minusDays(8).toEpochSecond(ZoneOffset.UTC)))

        then:
        RouteFileRepository.fileIsStale(testFilePath)

        when:
        Files.setLastModifiedTime(testFile.toPath(), FileTime.fromMillis(LocalDateTime.now().minusDays(6).toEpochSecond(ZoneOffset.UTC)))

        then:
        !RouteFileRepository.fileIsStale(testFilePath)
    }
}
