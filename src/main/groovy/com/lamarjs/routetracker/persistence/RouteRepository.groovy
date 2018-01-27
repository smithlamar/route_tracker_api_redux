package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.data.cta.api.common.Route

interface RouteRepository {

    void saveRoutes(List<Route> routes)
    List<Route> getRoutes()
}
