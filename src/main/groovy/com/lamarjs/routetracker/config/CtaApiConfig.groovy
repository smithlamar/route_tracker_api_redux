package com.lamarjs.routetracker.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.lamarjs.routetracker.controller.RouteTrackerApiController
import com.lamarjs.routetracker.persistence.RouteDatabaseRepository
import com.lamarjs.routetracker.persistence.RouteRepository
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
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import javax.sql.DataSource

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE

@PropertySource("classpath:cta-api.properties")
@Configuration
class CtaApiConfig {

    @Bean
    RouteTrackerApiController routeTrackerApiController(CtaRouteAssembler ctaRouteAssembler, CtaApiRequestService ctaApiRequestService) {
        return new RouteTrackerApiController(ctaRouteAssembler, ctaApiRequestService)
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource)
    }

    @Bean
    DataSource dataSource(@Value("\${spring.datasource.driver-class-name}") String driver,
                          @Value("\${spring.datasource.url}") String url,
                          @Value("\${spring.datasource.username}") String username,
                          @Value("\${spring.datasource.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver)
        dataSource.setUrl(url)
        dataSource.setUsername(username)
        dataSource.setPassword(password)
        return dataSource
    }

    @Bean
    CtaRouteAssembler ctaRouteAssembler(CtaApiRequestService ctaApiRequestService,
                                        @Qualifier("routeDatabaseRepository") RouteRepository routeDatabaseRepository) {
        CtaRouteAssembler ctaRouteAssembler = new CtaRouteAssembler(ctaApiRequestService, routeDatabaseRepository)
        ctaRouteAssembler.initializeRoutes()
        return ctaRouteAssembler
    }

    @Bean
    RouteRepository routeDatabaseRepository(JdbcTemplate jdbcTemplate) {
        return new RouteDatabaseRepository(jdbcTemplate)
    }

    @Bean
    CtaApiRequestService ctaApiRequestService(RestTemplate restTemplate,
                                              @Qualifier("objectMapper") ObjectMapper objectMapper, CtaApiUriBuilder ctaApiUriBuilder) {
        return new CtaApiRequestService(restTemplate, objectMapper, ctaApiUriBuilder)
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false).configure(FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
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
