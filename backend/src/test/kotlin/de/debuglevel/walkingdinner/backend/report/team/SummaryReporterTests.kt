package de.debuglevel.walkingdinner.backend.report.team

import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.meeting.Meeting
import de.debuglevel.walkingdinner.backend.report.team.summary.SummaryReporter
import de.debuglevel.walkingdinner.backend.team.*
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
                        firstname = "firstname1",
                        lastname = "lastname1",
                    ),
                    mailAddress = MailAddress(value = "mail1"),
                    phoneNumber = PhoneNumber(number = "phone1")
                ),
                de.debuglevel.walkingdinner.backend.team.Cook(
                    name = Name(
                        firstname = "firstname2",
                        lastname = "lastname2",
                    ),
                    mailAddress = MailAddress(value = "mail2"),
                    phoneNumber = PhoneNumber(number = "phone2")
                ),
                "address1",
                Diet.Omnivore,
                listOf(CookingCapability.OmnivoreAppetizer),
                null,
                "city1",
                null
            )
            val meeting = Meeting(
                listOf(
                    cookingTeam,
                    Team(
                        null,
                        Cook(
                            name = Name(
                                firstname = "firstname3",
                                lastname = "lastname3",
                            ),
                            mailAddress = MailAddress(value = "mail3"),
                            phoneNumber = PhoneNumber(number = "phone3")
                        ),
                        Cook(
                            name = Name(
                                firstname = "firstname4",
                                lastname = "lastname4",
                            ),
                            mailAddress = MailAddress(value = "mail4"),
                            phoneNumber = PhoneNumber(number = "phone4")
                        ),
                        "address2",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city2",
                        null
                    ),
                    Team(
                        null,
                        Cook(
                            name = Name(
                                firstname = "firstname5",
                                lastname = "lastname5",
                            ),
                            mailAddress = MailAddress(value = "mail5"),
                            phoneNumber = PhoneNumber(number = "phone5")
                        ),
                        Cook(
                            name = Name(
                                firstname = "firstname6",
                                lastname = "lastname6",
                            ),
                            mailAddress = MailAddress(value = "mail6"),
                            phoneNumber = PhoneNumber(number = "phone6")
                        ),
                        "address3",
                        Diet.Omnivore,
                        listOf(),
                        null,
                        "city3",
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
        val assertReport =
            "[firstname1 lastname1 & firstname2 lastname2]\tfirstname3 lastname3 & firstname4 lastname4\tfirstname5 lastname5 & firstname6 lastname6"
        Assertions.assertThat(report).isEqualTo(assertReport)
    }

    @Test
    fun `generate report`() {
        // Arrange

        // Act
        val report = summaryReporter.generateReports(setOf(meeting))

        // Assert
        val assertReport =
            "== Course Vorspeise\n[firstname1 lastname1 & firstname2 lastname2]\tfirstname3 lastname3 & firstname4 lastname4\tfirstname5 lastname5 & firstname6 lastname6"
        Assertions.assertThat(report).isEqualTo(assertReport)
    }
}