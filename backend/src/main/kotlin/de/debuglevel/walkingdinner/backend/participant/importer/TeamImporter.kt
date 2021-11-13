package de.debuglevel.walkingdinner.backend.participant.importer

import de.debuglevel.walkingdinner.backend.participant.Team

interface TeamImporter {
    fun import(): List<Team>
}