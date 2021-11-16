package de.debuglevel.walkingdinner.backend.team.importer.csv

import com.opencsv.bean.CsvToBeanBuilder
import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.importer.TeamImporter
import mu.KotlinLogging

/**
 * Imports [Team]s from a [csv] string.
 */
class CsvTeamImporter(private val csv: String) : TeamImporter {
    private val logger = KotlinLogging.logger {}

    override fun import(): List<Team> {
        logger.debug("Importing teams...")

        val teams = getTeamDTOs()
            //.onEach { logger.debug("Imported team: address '${it.address}', city '${it.city}'") }
            .map { it.toTeam() }

        logger.debug("Imported ${teams.count()} teams")
        return teams
    }

    /**
     * Get [TeamDTO]s from the CSV file.
     */
    private fun getTeamDTOs(): List<TeamDTO> {
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