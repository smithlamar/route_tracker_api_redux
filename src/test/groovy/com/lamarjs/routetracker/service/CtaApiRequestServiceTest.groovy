package com.lamarjs.routetracker.service

import com.lamarjs.routetracker.BaseSpecification
import com.lamarjs.routetracker.config.CtaApiConfig
import org.springframework.test.context.ContextConfiguration
import spock.lang.Unroll

@ContextConfiguration(classes = [CtaApiConfig])
@Unroll
class CtaApiRequestServiceTest extends BaseSpecification {
}
