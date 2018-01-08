package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.data.cta.api.common.Route
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "routes", path = "routes")
interface JpaRouteRepository extends JpaRepository<Route, String> {

    Route findByRouteId(@Param("routeId") String routeId)
}
