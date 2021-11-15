package de.debuglevel.walkingdinner.backend

import de.debuglevel.walkingdinner.backend.team.CookingCapability
import de.debuglevel.walkingdinner.backend.team.Diet
import de.debuglevel.walkingdinner.backend.team.Name
import de.debuglevel.walkingdinner.backend.team.Team
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
                    name = "cook"
                ),
                mailAddress = MailAddress(value = "mail"),
                phoneNumber = PhoneNumber(number = "123")
            ),
            de.debuglevel.walkingdinner.backend.team.Cook(
                name = Name(
                    name = "cook"
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
                            name = "cook"
                        ),
                        mailAddress = MailAddress(value = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    de.debuglevel.walkingdinner.backend.team.Cook(
                        name = Name(
                            name = "cook"
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
                            name = "cook"
                        ),
                        mailAddress = MailAddress(value = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    de.debuglevel.walkingdinner.backend.team.Cook(
                        name = Name(
                            name = "cook"
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