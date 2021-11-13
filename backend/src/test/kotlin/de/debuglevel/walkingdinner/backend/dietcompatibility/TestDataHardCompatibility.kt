package de.debuglevel.walkingdinner.backend.dietcompatibility

import de.debuglevel.walkingdinner.backend.MailAddress
import de.debuglevel.walkingdinner.backend.Meeting
import de.debuglevel.walkingdinner.backend.PhoneNumber
import de.debuglevel.walkingdinner.backend.participant.Cook
import de.debuglevel.walkingdinner.backend.participant.Diet
import de.debuglevel.walkingdinner.backend.participant.Name
import de.debuglevel.walkingdinner.backend.participant.Team
import java.util.stream.Stream

object TestDataHardCompatibility {
    fun compatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        null,
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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
                        Cook(
                            name = Name(
                                name = "cook"
                            ),
                            mailAddress = MailAddress(value = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
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