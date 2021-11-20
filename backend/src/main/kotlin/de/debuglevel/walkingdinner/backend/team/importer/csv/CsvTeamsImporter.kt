package de.debuglevel.walkingdinner.backend.team.importer.csv

import com.opencsv.bean.CsvToBeanBuilder
import de.debuglevel.walkingdinner.backend.location.locator.DatabaseCacheGeolocator
import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.TeamService
import jakarta.inject.Singleton
import mu.KotlinLogging

/**
 * Imports [Team]s from a [csv] string.
 */
@Singleton
class CsvTeamsImporter(
    private val databaseCacheGeolocator: DatabaseCacheGeolocator,
    private val teamService: TeamService,
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Read [Team]s from the [csv] and initialize their Locations.
     */
    fun importTeams(csv: String): List<Team> {
        logger.debug("Importing teams...")

        val teams = getTeamDTOs(csv)
            //.onEach { logger.debug("Imported team: address '${it.address}', city '${it.city}'") }
            .map { it.toTeam() }
            .onEach { it.location = databaseCacheGeolocator.getLocation(it.address, it.city) }

        val savedTeams = teams.map { teamService.save(it) }

        logger.debug("Imported ${savedTeams.count()} teams")
        return savedTeams
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