# Soundtrap Project

## Setup
- Install MySQL 8.x.x locally and set it running with Brew in the background
- Create a database named `spotify` and set its encoding to be `utf8mb4` wiht SequelPro
- Run `mvn package` to generate a .jar package in /target
- Import the project into IntelliJ IDEA and edit configurations.
- Add `server configuration.yml` as the program arguments
- Click run to have dropwizard run in the background

## DBs
- `songs` holds the track id, timestamps, and sanitised track name
- `soundcloud_syncs` holds an audit of past efforts that the cronjob reviews hourly in case of any failures on the service

## Endpoints
- `GET localhost:8080/api/ping`
    - Quick healthcheck endpoint
- `GET localhost:8080/api/word`
    - Takes parameter `word` for specifying the query term
    - Takes parameters `from` and `to` in form `yyyyMMdd` for specifying date ranges
    - `localhost:8080/api/word?word=test&from=20180903&to=20180904`
- `GET localhost:8080/api/songs`
    - Shows a complete list of songs in the DB locally for debugging