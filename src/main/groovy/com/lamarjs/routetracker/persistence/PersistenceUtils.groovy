package com.lamarjs.routetracker.persistence

import java.time.LocalDateTime
import java.time.ZoneOffset

class PersistenceUtils {
    static boolean isOlderThanSevenDays(Long creationTime) {
        return creationTime < LocalDateTime.now().minusDays(7).toEpochSecond(ZoneOffset.UTC)
    }
}
