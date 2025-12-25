CREATE DATABASE smartgrid;

\c smartgrid;

CREATE TABLE halfhourlyaverages (
    window_start TIMESTAMP PRIMARY KEY,
    avg_wind DOUBLE PRECISION,
    avg_solar DOUBLE PRECISION
);
