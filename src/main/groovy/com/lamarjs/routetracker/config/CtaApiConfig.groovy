package com.lamarjs.routetracker.config

import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.Scope
import org.springframework.web.util.UriComponentsBuilder

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE

@PropertySource("classpath:cta-api.properties")
@Configuration
class CtaApiConfig {

    @Bean
    CtaApiRequestService ctaApiRequestService(CtaApiUriBuilder ctaApiUriBuilder) {
        return new CtaApiRequestService(ctaApiUriBuilder)
    }

    @Bean
    CtaApiUriBuilder ctaApiUriBuilder(
            @Qualifier("bustimeRoutesUriBuilder") UriComponentsBuilder routesUriBuilder,
            @Qualifier("bustimeDirectionsUriBuilder") UriComponentsBuilder directionsUriBuilder,
            @Qualifier("bustimeStopsUriBuilder") UriComponentsBuilder stopsUriBuilder,
            @Qualifier("bustimePredictionsUriBuilder") UriComponentsBuilder predictionsUriBuilder) {
        return new CtaApiUriBuilder(routesUriBuilder, directionsUriBuilder, stopsUriBuilder, predictionsUriBuilder)
    }

    @Bean
    String busApiKey(@Value("\${bus.api.key}") String key) {
        return key
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeBaseUri(@Value("\${bus.api.url.scheme}") String scheme,
            @Value("\${bus.api.url.host}") String host,
            @Value("\${bus.api.url.path.base}") String basePath,
            @Value("\${bus.api.url.query-name.key}") String queryNameKey,
            String busApikey,
            @Value("\${bus.api.url.query-name.response-format}") String queryNameResponseFormat,
            @Value("\${bus.api.response.format}") String format) {

        return UriComponentsBuilder.newInstance().scheme(scheme).host(host).path(basePath).
                queryParam(queryNameKey, busApikey)
                                   .queryParam(queryNameResponseFormat, format)
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeRoutesUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.routes}") String path) {
        return bustimeBaseUri.path(path);
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeDirectionsUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.directions}") String path,
            @Value("\${bus.api.url.query-name.route-code}") String queryNameRouteCode) {
        return bustimeBaseUri.path(path).queryParam(queryNameRouteCode, "{routeCode}")
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeStopsUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.stops}") String path,
            @Value("\${bus.api.url.query-name.route-code}") String queryNameRoute,
            @Value("\${bus.api.url.query-name.direction}") String queryNameDirection) {

        return bustimeBaseUri.path(path).queryParam(queryNameRoute, "{routeCode}").
                queryParam(queryNameDirection, "{direction}")
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimePredictionsUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.predictions}") String path,
            @Value("\${bus.api.url.query-name.route-code}") String queryNameRoute,
            @Value("\${bus.api.url.query-name.stop-id}") String queryNameStopId,
            @Value("\${bus.api.url.query-name.results-limit}") String queryNameResultsLimit) {

        return bustimeBaseUri.path(path).queryParam(queryNameRoute, "{routeCode}").
                queryParam(queryNameStopId, "{stopId}").
                queryParam(queryNameResultsLimit, "{resultsLimit}")
    }
}
