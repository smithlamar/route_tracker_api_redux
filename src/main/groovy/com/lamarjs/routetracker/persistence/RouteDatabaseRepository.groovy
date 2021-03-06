package com.lamarjs.routetracker.persistence

import com.lamarjs.routetracker.data.cta.api.common.Route
import com.lamarjs.routetracker.data.cta.api.common.Stop
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class RouteDatabaseRepository implements RouteRepository {

    String SAVE_ROUTES_SQL = "INSERT IGNORE INTO routes (routeId, routeName, routeColor, createdDateInEpochSeconds) " +
            "VALUES (?, ?, ?, ?);"
    String SAVE_STOPS_SQL = "INSERT IGNORE INTO stops (stopId, stopName, latitude, longitude, direction) " +
            "VALUES (?, ?, ?, ?, ?);"
    String SAVE_STOPS_MAP_SQL = "INSERT IGNORE INTO routes_stops_map (routeId, stopId) VALUES (?, ?)"

    String GET_ROUTES_SQL = "SELECT * FROM routes;"

    String GET_STOPS_SQL = "SELECT stops.* FROM stops JOIN routes_stops_map AS map ON map.stopId = stops.stopId " +
            "WHERE map.routeId = ?"
    String GET_OLDEST_ROUTE_SQL = "SELECT MIN(createdDateInEpochSeconds) FROM routes;"


    JdbcTemplate jdbcTemplate

    @Autowired
    RouteDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate
    }

    @Override
    void saveRoutes(List<Route> routes) {

        List<Object[]> saveRoutesValues = new ArrayList<>()
        List<Object[]> saveStopsValues = new ArrayList<>()
        List<Object[]> saveStopsMapValues = new ArrayList<>()

        routes.forEach({ route ->

            saveRoutesValues.add([route.getRouteId(), route.getRouteName(), route.getRouteColor(),
                                  route.getCreatedDateInEpochSeconds()].toArray()
            )

            route.getStops().forEach({ stop ->

                saveStopsValues.add([stop.getStopId(), stop.getStopName(), stop.getLatitude(), stop.getLongitude(),
                                     stop.getDirection()].toArray()
                )

                saveStopsMapValues.add([route.routeId, stop.stopId].toArray())
            })
        })

        jdbcTemplate.batchUpdate(SAVE_ROUTES_SQL, saveRoutesValues)
        jdbcTemplate.batchUpdate(SAVE_STOPS_SQL, saveStopsValues)
        jdbcTemplate.batchUpdate(SAVE_STOPS_MAP_SQL, saveStopsMapValues)
    }

    @Override
    List<Route> getRoutes() {
        RowMapper<Route> routesRowMapper = { resultSet, rowNum ->
            new Route(routeId: resultSet.getString("routeId"), routeName: resultSet.getString("routeName"),
                    routeColor: resultSet.getString("routeColor"),
                    createdDateInEpochSeconds: resultSet.getLong("createdDateInEpochSeconds")
            )
        }

        RowMapper<Stop> stopsRowMapper = { rs, rowNum ->
            new Stop(stopId: rs.getInt("stopId"), stopName: rs.getString("stopName"),
                    latitude: rs.getDouble("latitude"), longitude: rs.getDouble("longitude"),
                    direction: rs.getString("direction")
            )
        }

        List<Route> routes = jdbcTemplate.query(GET_ROUTES_SQL, routesRowMapper)

        routes.forEach({ route ->
            List<Stop> stops = jdbcTemplate.query(GET_STOPS_SQL, stopsRowMapper, route.getRouteId())
            route.setStops(stops)
        })

        return routes
    }

    @Override
    void deleteRoutes() {
        ["routes_stops_map", "stops", "routes"].forEach({ tableName ->
            jdbcTemplate.update("TRUNCATE TABLE ${tableName}")
        })
    }

    @Override
    boolean isStale() {

        RowMapper<Long> mapper = new RowMapper<Long>() {
            @Override
            Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return null
            }
        }

        RowMapper<Long> longMapper = { resultSet, rowNum ->
            return resultSet?.getLong(1)
        }
        Long oldestRouteDate = jdbcTemplate.query(GET_OLDEST_ROUTE_SQL, longMapper).get(0)
        return oldestRouteDate == null || PersistenceUtils.isOlderThanSevenDays(oldestRouteDate)
    }
}
