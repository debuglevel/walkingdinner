package de.debuglevel.walkingdinner.backend.team.importer

import de.debuglevel.walkingdinner.backend.team.Team

interface TeamImporter {
    fun import(): List<Team>
}