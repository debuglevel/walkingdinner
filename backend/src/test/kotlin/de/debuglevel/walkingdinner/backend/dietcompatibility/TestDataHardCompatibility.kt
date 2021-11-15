package de.debuglevel.walkingdinner.backend.dietcompatibility

import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.team.Diet
import de.debuglevel.walkingdinner.backend.team.Name
import de.debuglevel.walkingdinner.backend.team.Team
import java.util.stream.Stream

object TestDataHardCompatibility {
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
                "course"
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
                "course"
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
                "course"
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
                "course"
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
                "course"
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
                "course"
            )
        )
    )

    data class MeetingData(
        val meeting: Meeting
    )
}