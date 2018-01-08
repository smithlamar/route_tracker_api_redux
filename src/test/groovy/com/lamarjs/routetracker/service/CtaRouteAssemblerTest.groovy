package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.data.cta.api.common.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CtaRouteAssemblerTest extends Specification {

    @Autowired
    CtaRouteAssembler ctaRouteAssembler

    def "should initialze routes"() {

        when:
        List<Route> routes = ctaRouteAssembler.initializeRoutes()

        then:
        routes
        routes.size() > 0
    }

    def "should load routes from file"() {

    }
}
