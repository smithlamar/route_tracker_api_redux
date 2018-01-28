CREATE TABLE IF NOT EXISTS routes (
  routeId VARCHAR2(12) NOT NULL,
  routeName VARCHAR2(128) NOT NULL,
  routeColor VARCHAR2(7) NOT NULL,
  createdDateInEpochSeconds BIGINT NOT NULL,
  CONSTRAINT primary_key PRIMARY KEY (routeId, routeName),
  CONSTRAINT secondary_index UNIQUE (routeName)
);

CREATE TABLE IF NOT EXISTS stops (
  stopId INT(12) NOT NULL,
  stopName VARCHAR2(128) NOT NULL,
  latitude DECIMAL(3, 8) NOT NULL,
  longitude DECIMAL(3, 8) NOT NULL,
  direction VARCHAR2(12) NOT NULL,
  CONSTRAINT primary_key PRIMARY KEY (stopId),
  CONSTRAINT secondary_index UNIQUE (stopName)
);

CREATE TABLE IF NOT EXISTS routes_stops_map (
  routeId VARCHAR2(12) NOT NULL,
  stopId INT(12) NOT NULL,
  CONSTRAINT primary_key PRIMARY KEY (routeId, stopId),
  CONSTRAINT foreign_key_routes FOREIGN KEY (routeId) REFERENCES routes(routeId),
  CONSTRAINT foreign_key_stops FOREIGN KEY (stopId) REFERENCES stops(stopId)
);