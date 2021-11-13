package de.debuglevel.walkingdinner.backend.plan.report.teams.summary

import de.debuglevel.walkingdinner.backend.MailAddress
import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.PhoneNumber
import de.debuglevel.walkingdinner.backend.participant.*
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SummaryReporterTests {

    @Inject
    lateinit var summaryReporter: SummaryReporter

    private val meeting: Meeting
        get() {
            val cookingTeam = Team(
                null,
                Cook(
                    name = Name(
                        name = "cook1"
                    ),
                    mailAddress = MailAddress(value = "mail"),
                    phoneNumber = PhoneNumber(number = "123")
                ),
                Cook(
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
                        Cook(
                            name = Name(
                                name = "cook3"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook5"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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