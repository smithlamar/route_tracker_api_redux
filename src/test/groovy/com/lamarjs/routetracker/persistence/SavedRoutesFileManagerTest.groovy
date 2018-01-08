package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification

class SavedRoutesFileManagerTest extends BaseSpecification {

    @Shared
    List<Route> testRoutes
    String testFilePath = "./testRoutesFile.json"

    @Autowired
    SavedRoutesFileManager savedRoutesFileManager

    void setupSpec() {
        testRoutes = new ArrayList<>()
        List<Stop> testStops = new ArrayList<>([new Stop(stopId: 1, stopName: "testStop", direction: new Direction(direction: Direction.NORTHBOUND))])
        testRoutes.add(new Route(routeId: 1, routeName: "test", stops: testStops))
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
        File testFile = new File(testFilePath)

        expect:
        testFile.exists()
        testFile.length() > 0
        println(testFile.getText())
    }

    def "SavedRoutesFileIsStale"() {
    }
}
