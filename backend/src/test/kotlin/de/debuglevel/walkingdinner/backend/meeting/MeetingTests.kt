package de.debuglevel.walkingdinner.backend.meeting

import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.team.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeetingTests {
    fun `cooking team`() {
        // Arrange
        val cookingTeam = Team(
            null,
            de.debuglevel.walkingdinner.backend.team.Cook(
                name = Name(
                    firstname = "firstname",
                    lastname = "lastname",
                ),
                mailAddress = MailAddress(value = "mail"),
                phoneNumber = PhoneNumber(number = "123")
            ),
            de.debuglevel.walkingdinner.backend.team.Cook(
                name = Name(
                    firstname = "firstname",
                    lastname = "lastname",
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
                            firstname = "firstname",
                            lastname = "lastname",
                        ),
                        mailAddress = MailAddress(value = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    Cook(
                        name = Name(
                            firstname = "firstname",
                            lastname = "lastname",
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
                            firstname = "firstname",
                            lastname = "lastname",
                        ),
                        mailAddress = MailAddress(value = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    Cook(
                        name = Name(
                            firstname = "firstname",
                            lastname = "lastname",
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

        // Act

        // Assert
        Assertions.assertThat(meeting.getCookingTeam()).isEqualTo(cookingTeam)
    }
}