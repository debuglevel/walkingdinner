package de.debuglevel.walkingdinner.backend.team.importer

import de.debuglevel.walkingdinner.backend.location.locator.DatabaseCacheGeolocator
import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.importer.csv.CsvTeamsImporter
import jakarta.inject.Singleton
import mu.KotlinLogging

/**
 * @implNote Seems to be used when uploading a CSV and everything must be initialized first.
 */
@Singleton
class DatabaseBuilder(private val databaseCacheGeolocator: DatabaseCacheGeolocator) {
    private val logger = KotlinLogging.logger {}

    /**
     * Read [Team]s from the [csv] and initialize their Locations.
     */
    fun getTeams(csv: String): List<Team> {
        logger.debug { "Getting teams..." }

        // Read/Import teams from the CSV
        val teams = CsvTeamsImporter(csv).import()
        populateTeamLocations(teams)

        logger.debug { "Got ${teams.count()} teams" }
        return teams
    }

    /**
     * Populates the [Team]s' Locations by their provided address.
     */
    private fun populateTeamLocations(teams: List<Team>) {
        logger.debug { "Populating team locations..." }

        teams
            //.parallelStream()
            //.onEach { logger.debug("== Processing: city '${it.city}',\taddress '${it.address}'\t$it") }
            .forEach {
                //logger.debug("== Processing: city '${it.city}',\taddress '${it.address}'\t$it")
                databaseCacheGeolocator.populateLocation(it)
            }

        logger.debug { "Populated team locations" }
    }
}