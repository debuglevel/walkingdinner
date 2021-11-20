package de.debuglevel.walkingdinner.backend.team.importer.csv

import com.opencsv.bean.CsvToBeanBuilder
import de.debuglevel.walkingdinner.backend.location.locator.DatabaseCacheGeolocator
import de.debuglevel.walkingdinner.backend.team.Team
import jakarta.inject.Singleton
import mu.KotlinLogging

/**
 * Imports [Team]s from a [csv] string.
 */
@Singleton
class CsvTeamsImporter(private val databaseCacheGeolocator: DatabaseCacheGeolocator) {
    private val logger = KotlinLogging.logger {}

    /**
     * Read [Team]s from the [csv] and initialize their Locations.
     */
    fun getTeams(csv: String): List<Team> {
        logger.debug("Importing teams...")

        val teams = getTeamDTOs(csv)
            //.onEach { logger.debug("Imported team: address '${it.address}', city '${it.city}'") }
            .map { it.toTeam() }
            .onEach { it.location = databaseCacheGeolocator.getLocation(it.address, it.city) }

        logger.debug("Imported ${teams.count()} teams")
        return teams
    }

    /**
     * Get [TeamDTO]s from the CSV file.
     */
    private fun getTeamDTOs(csv: String): List<TeamDTO> {
        return csv.reader().use { reader ->
            try {
                val csvToBean = CsvToBeanBuilder<TeamDTO>(reader)
                    .withType(TeamDTO::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

                val teamDTOs = csvToBean.parse()
                teamDTOs
            } catch (e: Exception) {
                logger.error("Error occurred while reading CSV", e)
                throw e
            }
        }
    }
}