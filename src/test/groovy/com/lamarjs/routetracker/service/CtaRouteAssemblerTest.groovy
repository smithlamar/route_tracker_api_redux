package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.persistence.RouteRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore

class CtaRouteAssemblerTest extends BaseSpecification {

    @Autowired
    CtaRouteAssembler ctaRouteAssembler
    @Autowired
    RouteRepository routeRepository

    void cleanup() {
        routeRepository.deleteRoutes()
    }

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

    def "should load routes from repository"() {
        routeRepository.saveRoutes(testRoutes)
        List<Route> actualRoutes = ctaRouteAssembler.initializeRoutes()
        expect:
        actualRoutes.toString() == testRoutes.toString()
        ctaRouteAssembler.getRoutesMap().size() > 0
        ctaRouteAssembler.getRoutesMap().get(testRoutes.get(0).getRouteId())
    }
}
