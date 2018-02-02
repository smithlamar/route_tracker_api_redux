CREATE TABLE IF NOT EXISTS routes (
  routeId VARCHAR(12) NOT NULL,
  routeName VARCHAR(128) NOT NULL,
  routeColor VARCHAR(7) NOT NULL,
  createdDateInEpochSeconds BIGINT NOT NULL,
  CONSTRAINT primary_key_routes PRIMARY KEY (routeId, routeName)
);

CREATE TABLE IF NOT EXISTS stops (
  stopId INT(12) NOT NULL,
  stopName VARCHAR(128) NOT NULL,
  latitude DECIMAL(13, 10) NOT NULL,
  longitude DECIMAL(13, 10) NOT NULL,
  direction VARCHAR(12) NOT NULL,
  CONSTRAINT primary_key_stops PRIMARY KEY (stopId, stopName)
);

CREATE TABLE IF NOT EXISTS routes_stops_map (
  routeId VARCHAR(12) NOT NULL,
  stopId INT(12) NOT NULL,
  CONSTRAINT primary_key_map PRIMARY KEY (routeId, stopId)
);