package de.debuglevel.walkingdinner.backend.dietcompatibility

import de.debuglevel.walkingdinner.backend.MailAddress
import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.PhoneNumber
import de.debuglevel.walkingdinner.backend.team.CookingCapability
import de.debuglevel.walkingdinner.backend.team.Diet
import de.debuglevel.walkingdinner.backend.team.Name
import de.debuglevel.walkingdinner.backend.team.Team
import java.util.stream.Stream

object TestDataCourseCompatibility {
    fun compatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
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
                        listOf(CookingCapability.OmnivoreAppetizer),
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
        ),
        MeetingData(
            Meeting(
                listOf(
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
                        Diet.Vegan,
                        listOf(CookingCapability.VeganMaindish),
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
                        Diet.Vegetarian,
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
                "Hauptspeise"
            )
        ), MeetingData(
            Meeting(
                listOf(
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
                        Diet.Vegetarian,
                        listOf(CookingCapability.VeganDessert),
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
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city",
                        null
                    )
                ),
                "Dessert"
            )
        )
    )

    fun incompatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
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
                        Diet.Vegetarian,
                        listOf(CookingCapability.VegetarianAppetizer),
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
                        Diet.Vegan,
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
        ),
        MeetingData(
            Meeting(
                listOf(
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
                        listOf(CookingCapability.OmnivoreMaindish, CookingCapability.VegetarianMaindish),
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
                        Diet.Vegan,
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
                        Diet.Vegetarian,
                        listOf(),
                        null,
                        "city",
                        null
                    )
                ),
                "Hauptspeise"
            )
        ), MeetingData(
            Meeting(
                listOf(
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
                        listOf(CookingCapability.VegetarianDessert),
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
                        Diet.Vegan,
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
                        Diet.Vegan,
                        listOf(),
                        null,
                        "city",
                        null
                    )
                ),
                "Dessert"
            )
        )
    )

    data class MeetingData(
        val meeting: Meeting
    )
}