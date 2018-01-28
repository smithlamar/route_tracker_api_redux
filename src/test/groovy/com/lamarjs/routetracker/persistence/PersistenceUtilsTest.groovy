package com.lamarjs.routetracker.persistence

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset

class PersistenceUtilsTest extends Specification {
    Long unixTimeOlderThan7Days = LocalDateTime.now().minusDays(7).toEpochSecond(ZoneOffset.UTC)
    Long unixTimeNotOlderThan7Days = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    def '#unixDateTime is older than seven days should return #result'() {

        expect:
        PersistenceUtils.isOlderThanSevenDays(unixDateTime) == result

        where:
        unixDateTime                   | result
        LocalDateTime.now().minusDays(8).toEpochSecond(ZoneOffset.UTC)   | true
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)      | false
    }
}
