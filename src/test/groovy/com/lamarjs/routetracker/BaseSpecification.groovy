package com.lamarjs.routetracker

import com.lamarjs.routetracker.data.cta.api.common.Direction
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset

@SpringBootTest
class BaseSpecification extends Specification {
    @Shared
    static List<Route> testRoutes = new ArrayList<>()
    static Map<String, String> jsonSampleFilesAsStrings
    static Map<String, Map<String, Object>> jsonSamplesAsMaps

    void setupSpec() {
        List<Stop> testStops = new ArrayList<>([new Stop(stopId: 1, stopName: "testStop", latitude: 1.0, longitude: 1.0, direction: new Direction(direction: Direction.NORTHBOUND))])
        testRoutes.add(new Route(routeId: "1", routeName: "test", routeColor: "000000", stops: testStops, createdDateInEpochSeconds: LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)))

        List<File> jsonSampleUris = new File('src/test/resources/sampledata/bustimeapi/response/json/').listFiles().
                toList()

        jsonSampleFilesAsStrings = new HashMap<>()
        jsonSamplesAsMaps = new HashMap<>()
        JsonSlurper slurper = new JsonSlurper()

        jsonSampleUris.forEach({ file ->
            jsonSampleFilesAsStrings.put(file.getName(), file.getText())
            jsonSamplesAsMaps.put(file.getName(), slurper.parse(file) as Map<String, Object>)
        })
    }
}
