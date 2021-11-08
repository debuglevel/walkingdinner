package de.debuglevel.walkingdinner.rest.plan.report.teams

import de.debuglevel.walkingdinner.rest.participant.Team

/**
 * Report for a [team] containing information in the [plaintext].
 */
data class TextReport(
    val team: Team,
    val plaintext: String
)