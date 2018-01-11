package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import java.nio.file.Files
import java.nio.file.attribute.FileTime
import java.time.LocalDate

class SavedRoutesFileManagerTest extends BaseSpecification {

    @Shared
    String testFilePath = "./testRoutesFile.json"

    @Autowired
    SavedRoutesFileManager savedRoutesFileManager

    void setup() {
    }

    void cleanup() {
        new File(testFilePath).delete()
    }

    def "saveRoutesCreatesAppropriateFileStructure"() {
        File savedRoutesJsonPath = new File(savedRoutesFileManager.routesJsonFilePath)
        savedRoutesJsonPath.delete()

        savedRoutesFileManager.saveRoutes(testRoutes)

        expect:
        savedRoutesJsonPath.exists()
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

    def "SavedRoutesFileIsStale"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, testFilePath)
        File testFile = new File(testFilePath)
        long thirtySecsAsMilli = 1000L * 30
        Files.setLastModifiedTime(testFile.toPath(), FileTime.fromMillis((LocalDate.now().minusDays(8).toEpochDay())))

        expect:
        SavedRoutesFileManager.fileIsStale(testFilePath)
    }
}
