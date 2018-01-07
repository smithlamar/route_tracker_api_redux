package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Route
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification

class SavedRoutesFileManagerTest extends BaseSpecification {

    @Shared
    List<Route> testRoutes
    String testFilePath = "./cache/testRoutesFile.json"

    @Autowired
    SavedRoutesFileManager savedRoutesFileManager

    void setupSpec() {
        testRoutes = new ArrayList<>()
        testRoutes.add(new Route(routeId: 1, routeName: "test"))
    }

    void setup() {
    }

    void cleanup() {
        new File(testFilePath).delete()
    }

    def "LoadRoutes"() {
    }

    def "SaveRoutes"() {
    }

    def "LoadRoutesFromFile"() {

    }

    def "SaveRoutesToFile"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, testFilePath)

        expect:
        new File(testFilePath).exists()
    }

    def "SavedRoutesFileIsStale"() {
    }
}
