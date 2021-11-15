package de.debuglevel.walkingdinner.backend.report.team.gmail

import com.google.api.services.gmail.model.Draft
import de.debuglevel.walkingdinner.backend.plan.Plan
import de.debuglevel.walkingdinner.backend.report.Report
import java.util.*

data class GmailDraftsReport(
    override val id: UUID?,
    override val plan: Plan,
    val drafts: MutableSet<Draft> = mutableSetOf()
) : Report