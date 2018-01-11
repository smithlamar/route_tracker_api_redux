package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.persistence.SavedRoutesFileManager
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore

class CtaRouteAssemblerTest extends BaseSpecification {

    @Autowired
    CtaRouteAssembler ctaRouteAssembler
    @Autowired
    SavedRoutesFileManager savedRoutesFileManager

    def "should initialze routes"() {

        when:
        List<Route> routes = ctaRouteAssembler.initializeRoutes()

        then:
        routes
        routes.size() > 0
    }

    @Ignore
    def "should initialize routes from cta api request service"() {
        List<Route> routes = ctaRouteAssembler.loadRoutesFromCtaApi()

        expect:
        routes
        routes.size() > 100
    }

    @Ignore
    def "should load routes from file"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, savedRoutesFileManager.routesJsonFilePath)

        List<Route> actualRoutes = ctaRouteAssembler.initializeRoutes()
        new File(savedRoutesFileManager.routesJsonFilePath).delete()
        expect:
        actualRoutes.toString() == testRoutes.toString()
        ctaRouteAssembler.getAssembledRoutes().size() > 0
        ctaRouteAssembler.getAssembledRoutes().get(testRoutes.get(0).getRouteId())
    }
}
