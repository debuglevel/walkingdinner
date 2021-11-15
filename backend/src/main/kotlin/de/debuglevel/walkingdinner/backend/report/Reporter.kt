package de.debuglevel.walkingdinner.backend.report

import de.debuglevel.walkingdinner.backend.meeting.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}