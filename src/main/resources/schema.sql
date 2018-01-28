CREATE TABLE IF NOT EXISTS routes (
  routeId VARCHAR2(12) NOT NULL,
  routeName VARCHAR2(128) NOT NULL,
  routeColor VARCHAR2(7) NOT NULL,
  createdDateInEpochSeconds BIGINT NOT NULL,
  CONSTRAINT primary_key_routes PRIMARY KEY (routeId, routeName),
  CONSTRAINT secondary_index_routes UNIQUE (routeName)
);

CREATE TABLE IF NOT EXISTS stops (
  stopId INT(12) NOT NULL,
  stopName VARCHAR2(128) NOT NULL,
  latitude DECIMAL(13, 10) NOT NULL,
  longitude DECIMAL(13, 10) NOT NULL,
  direction VARCHAR2(12) NOT NULL,
  CONSTRAINT primary_key_stops PRIMARY KEY (stopId),
  CONSTRAINT secondary_index_stops UNIQUE (stopName)
);

CREATE TABLE IF NOT EXISTS routes_stops_map (
  routeId VARCHAR2(12) NOT NULL,
  stopId INT(12) NOT NULL,
  CONSTRAINT primary_key_map PRIMARY KEY (routeId, stopId)
);