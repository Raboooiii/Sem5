-- Load the data assuming space/tab separated values
weather = LOAD 'weather_data.txt' USING PigStorage('\t') AS (station_id:chararray, date:chararray, temperature:int);

-- Extract year from date (assuming date format is YYYYMMDD)
weather_with_year = FOREACH weather GENERATE station_id, SUBSTRING(date, 0, 4) AS year, temperature;

-- Group data by year
grouped_by_year = GROUP weather_with_year BY year;

-- Calculate max temperature for each year
max_temp_by_year = FOREACH grouped_by_year GENERATE
    group AS year,
    MAX(weather_with_year.temperature) AS max_temperature;

-- Store or dump results
DUMP max_temp_by_year;
-- or STORE max_temp_by_year INTO 'output_max_temp_by_year';
