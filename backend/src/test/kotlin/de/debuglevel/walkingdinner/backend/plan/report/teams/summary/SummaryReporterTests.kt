package de.debuglevel.walkingdinner.backend.plan.report.teams.summary

import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.team.CookingCapability
import de.debuglevel.walkingdinner.backend.team.Diet
import de.debuglevel.walkingdinner.backend.team.Name
import de.debuglevel.walkingdinner.backend.team.Team
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SummaryReporterTests {

    @Inject
    lateinit var summaryReporter: SummaryReporter

    private val meeting: Meeting
        get() {
            val cookingTeam = Team(
                null,
                de.debuglevel.walkingdinner.backend.team.Cook(
                    name = Name(
                        name = "cook1"
                    ),
                    mailAddress = MailAddress(value = "mail"),
                    phoneNumber = PhoneNumber(number = "123")
                ),
                de.debuglevel.walkingdinner.backend.team.Cook(
                    name = Name(
                        name = "cook2"
                    ),
                    mailAddress = MailAddress(value = "mail"),
                    phoneNumber = PhoneNumber(number = "123")
                ),
                "address",
                Diet.Omnivore,
                listOf(CookingCapability.OmnivoreAppetizer),
                null,
                "city",
                null
            )
            val meeting = Meeting(
                listOf(
                    cookingTeam,
                    Team(
                        null,
                        de.debuglevel.walkingdinner.backend.team.Cook(
                            name = Name(
                                name = "cook3"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        de.debuglevel.walkingdinner.backend.team.Cook(
                            name = Name(
                                name = "cook4"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city",
                        null
                    ),
                    Team(
                        null,
                        de.debuglevel.walkingdinner.backend.team.Cook(
                            name = Name(
                                name = "cook5"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        de.debuglevel.walkingdinner.backend.team.Cook(
                            name = Name(
                                name = "cook6"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city",
                        null
                    )
                ),
                "Vorspeise"
            )

            return meeting
        }

    @Test
    fun `meeting to string`() {
        // Arrange

        // Act
        val report = summaryReporter.meetingToString(meeting)

        // Assert
        val assertReport = "[cook1 & cook2]\tcook3 & cook4\tcook5 & cook6"
        Assertions.assertThat(report).isEqualTo(assertReport)
    }

    @Test
    fun `generate report`() {
        // Arrange

        // Act
        val report = summaryReporter.generateReports(setOf(meeting))

        // Assert
        val assertReport = "== Course Vorspeise\n[cook1 & cook2]\tcook3 & cook4\tcook5 & cook6"
        Assertions.assertThat(report).isEqualTo(assertReport)
    }
}