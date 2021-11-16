package de.debuglevel.walkingdinner.backend.team.importer

import de.debuglevel.walkingdinner.backend.team.Team

/**
 * Imports [Team]s.
 */
interface TeamImporter {
    /**
     * Imports the [Team]s.
     */
    fun import(): List<Team>
}