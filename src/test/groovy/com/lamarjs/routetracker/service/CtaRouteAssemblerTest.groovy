package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.persistence.SavedRoutesFileManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
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

    def "should load routes from file"() {
        savedRoutesFileManager.saveRoutesToFile(testRoutes, savedRoutesFileManager.routesJsonFilePath)

        List<Route> actualRoutes = ctaRouteAssembler.initializeRoutes()

        expect:
        actualRoutes == testRoutes
        ctaRouteAssembler.getAssembledRoutes() > 0
        ctaRouteAssembler.getAssembledRoutes().get(testRoutes.routeId)
    }
}
