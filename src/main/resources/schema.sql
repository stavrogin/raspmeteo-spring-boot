CREATE TABLE IF NOT EXISTS DATA_SOURCE (
	datasource_id SMALLINT PRIMARY KEY auto_increment,
	name VARCHAR NULL
);

CREATE TABLE IF NOT EXISTS WEATHER_DATA (
	weatherdata_id BIGINT PRIMARY KEY auto_increment, 
	ts TIMESTAMP NOT NULL,
	pressure DOUBLE NULL,
	temperature DOUBLE NULL,
	altitude DOUBLE NULL,
	description VARCHAR NULL,
	datasource_id SMALLINT,
	FOREIGN KEY (datasource_id) REFERENCES DATA_SOURCE(datasource_id)
);

CREATE INDEX IF NOT EXISTS WEATHER_DATA_IDX_1 ON WEATHER_DATA(ts);