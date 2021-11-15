package de.debuglevel.walkingdinner.backend.plan.report.teams

import de.debuglevel.walkingdinner.backend.team.Team

/**
 * Report for a [team] containing information in the [plaintext].
 */
data class TextReport(
    val team: Team,
    val plaintext: String
)