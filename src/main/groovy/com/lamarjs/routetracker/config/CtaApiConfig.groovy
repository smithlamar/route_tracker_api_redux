package com.lamarjs.routetracker.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.lamarjs.routetracker.persistence.SavedRoutesFileManager
import com.lamarjs.routetracker.service.CtaApiRequestService
import com.lamarjs.routetracker.service.CtaRouteAssembler
import com.lamarjs.routetracker.util.CtaApiUriBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.Scope
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE

@PropertySource("classpath:cta-api.properties")
@Configuration
class CtaApiConfig {

    @Bean
    SavedRoutesFileManager(
            @Qualifier("objectMapper") ObjectMapper objectMapper,
            @Qualifier("savedRoutesJsonFilePath") String savedRoutesJsonFilePath) {
        return new SavedRoutesFileManager(objectMapper, savedRoutesJsonFilePath)
    }

    @Bean
    CtaRouteAssembler ctaRouteAssembler(CtaApiRequestService ctaApiRequestService) {
        return new CtaRouteAssembler(ctaApiRequestService)
    }

    @Bean
    CtaApiRequestService ctaApiRequestService(RestTemplate restTemplate,
                                              @Qualifier("objectMapper") ObjectMapper objectMapper, CtaApiUriBuilder ctaApiUriBuilder) {
        return new CtaApiRequestService(restTemplate, objectMapper, ctaApiUriBuilder)
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }


    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build()
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    CtaApiUriBuilder ctaApiUriBuilder(
            @Qualifier("bustimeRoutesUriBuilder") UriComponentsBuilder routesUriBuilder,
            @Qualifier("bustimeDirectionsUriBuilder") UriComponentsBuilder directionsUriBuilder,
            @Qualifier("bustimeStopsUriBuilder") UriComponentsBuilder stopsUriBuilder,
            @Qualifier("bustimePredictionsUriBuilder") UriComponentsBuilder predictionsUriBuilder,
            @Qualifier("defaultPredictionLimit") int defaultPredictionLimit) {

        return new CtaApiUriBuilder(routesUriBuilder, directionsUriBuilder, stopsUriBuilder, predictionsUriBuilder,
                defaultPredictionLimit)
    }

    @Bean
    int defaultPredictionLimit(@Value("\${bus.api.defaults.prediction-limit}") String limit) {
        return limit as int
    }

    @Bean
    String busApiKey(@Value("\${bus.api.key}") String key) {
        return key
    }

    @Bean
    String savedRoutesJsonFilePath(@Value("\${persistence.routes.json.file.path}") String path) {
        return path
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeBaseUri(@Value("\${bus.api.url.scheme}") String scheme,
                                        @Value("\${bus.api.url.host}") String host,
                                        @Value("\${bus.api.url.path.base}") String basePath,
                                        @Value("\${bus.api.url.query-name.key}") String queryNameKey,
                                        @Qualifier("busApiKey") String busApikey,
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
        return bustimeBaseUri.path(path)
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeDirectionsUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.directions}") String path,
            @Value("\${bus.api.url.query-name.routeId}") String queryNameRouteId) {
        return bustimeBaseUri.path(path).queryParam(queryNameRouteId, "{RouteId}")
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimeStopsUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.stops}") String path,
            @Value("\${bus.api.url.query-name.routeId}") String queryNameRouteId,
            @Value("\${bus.api.url.query-name.direction}") String queryNameDirection) {

        return bustimeBaseUri.path(path).queryParam(queryNameRouteId, "{routeId}").
                queryParam(queryNameDirection, "{direction}")
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    UriComponentsBuilder bustimePredictionsUriBuilder(
            @Qualifier("bustimeBaseUri") UriComponentsBuilder bustimeBaseUri,
            @Value("\${bus.api.url.path.predictions}") String path,
            @Value("\${bus.api.url.query-name.routeId}") String queryNameRouteId,
            @Value("\${bus.api.url.query-name.stop-id}") String queryNameStopId,
            @Value("\${bus.api.url.query-name.results-limit}") String queryNameResultsLimit) {

        return bustimeBaseUri.path(path).queryParam(queryNameRouteId, "{RouteId}").
                queryParam(queryNameStopId, "{stopId}").
                queryParam(queryNameResultsLimit, "{resultsLimit}")
    }
}
