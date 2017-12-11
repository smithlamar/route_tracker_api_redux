package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.config.CtaApiConfig
import com.lamarjs.routetracker.model.cta.api.common.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
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
}
