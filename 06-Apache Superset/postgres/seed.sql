CREATE DATABASE smartgrid;

\c smartgrid;

CREATE TABLE halfhourlyaverages (
    window_start TIMESTAMP PRIMARY KEY,
    avg_wind DOUBLE PRECISION,
    avg_solar DOUBLE PRECISION
);

CREATE TABLE rawdata(
    timestamp TIMESTAMP PRIMARY KEY,

    voltage_v DOUBLE PRECISION,
    current_a DOUBLE PRECISION,
    power_kw DOUBLE PRECISION,
    reactive_power_kvar DOUBLE PRECISION,
    power_factor DOUBLE PRECISION,

    solar_kw DOUBLE PRECISION,
    wind_kw DOUBLE PRECISION,

    grid_in_kw DOUBLE PRECISION,
    grid_out_kw DOUBLE PRECISION,

    voltage_fluct_percent DOUBLE PRECISION,

    fault_indicator VARCHAR(50),
    fault_num INTEGER,

    temperature_c DOUBLE PRECISION,
    humidity_percent DOUBLE PRECISION,

    electricity_price_gbp_per_kwh DOUBLE PRECISION
)
