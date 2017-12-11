package com.lamarjs.routetracker

import com.lamarjs.routetracker.model.cta.api.bus.BustimeResponse
import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

@RunWith(SpringRunner)
@SpringBootTest
class RouteTrackerApiApplicationTests {

    @Autowired
    CtaApiUriBuilder builder

    @Autowired
    CtaApiRequestService requestService

    @Test
    void contextLoads() {
    }
}
