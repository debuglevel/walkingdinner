package de.debuglevel.walkingdinner.backend.team.importer

import de.debuglevel.walkingdinner.backend.team.Team

/**
 * Imports [Team]s.
 */
interface TeamsImporter {
    /**
     * Imports the [Team]s.
     */
    fun import(): List<Team>
}