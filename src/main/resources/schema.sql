CREATE TABLE IF NOT EXISTS DATA_SOURCE (
	datasource_id SMALLINT PRIMARY KEY NOT NULL,
	name VARCHAR NULL
);

CREATE TABLE IF NOT EXISTS WEATHER_DATA (
	id BIGINT PRIMARY KEY auto_increment, 
	ts TIMESTAMP NOT NULL,
	pressure REAL NULL,
	temperature REAL NULL,
	altitude REAL NULL,
	description VARCHAR NULL,
	datasource_id SMALLINT,
	FOREIGN KEY (datasource_id) REFERENCES DATA_SOURCE(datasource_id)
);

CREATE INDEX IF NOT EXISTS WEATHER_DATA_IDX_1 ON WEATHER_DATA(ts);