package de.debuglevel.walkingdinner.backend.team.importer.csv

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.location.Location
import de.debuglevel.walkingdinner.backend.team.CookingCapability
import de.debuglevel.walkingdinner.backend.team.Diet
import de.debuglevel.walkingdinner.backend.team.Name
import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.importer.csv.converter.CapabilitiesConverter
import de.debuglevel.walkingdinner.backend.team.importer.csv.converter.MailAddressConverter
import de.debuglevel.walkingdinner.backend.team.importer.csv.converter.NameConverter
import de.debuglevel.walkingdinner.backend.team.importer.csv.converter.PhoneNumberConverter

class TeamDTO {
    @CsvCustomBindByName(column = "Koch1", converter = NameConverter::class)
    lateinit var name1: Name

    @CsvCustomBindByName(column = "Koch2", converter = NameConverter::class)
    lateinit var name2: Name

    @CsvCustomBindByName(column = "Telefon1", converter = PhoneNumberConverter::class)
    lateinit var phone1: PhoneNumber

    @CsvCustomBindByName(column = "Telefon2", converter = PhoneNumberConverter::class)
    lateinit var phone2: PhoneNumber

    @CsvCustomBindByName(column = "Mail1", converter = MailAddressConverter::class)
    lateinit var mail1: MailAddress

    @CsvCustomBindByName(column = "Mail2", converter = MailAddressConverter::class)
    lateinit var mail2: MailAddress

    @CsvBindByName(column = "Adresse")
    lateinit var address: String

    @CsvBindByName(column = "Stadt")
    lateinit var city: String

    @CsvBindByName(column = "Diet")
    lateinit var dietString: String

    private val diet: Diet
        get() = Diet.valueOf(dietString)

    @CsvCustomBindByName(column = "Capabilities", converter = CapabilitiesConverter::class)
    val cookingCapabilities: List<CookingCapability?> = listOf()

    fun toTeam(): Team {
        val cook1 =
            de.debuglevel.walkingdinner.backend.team.Cook(name = name1, mailAddress = mail1, phoneNumber = phone1)
        val cook2 =
            de.debuglevel.walkingdinner.backend.team.Cook(name = name2, mailAddress = mail2, phoneNumber = phone2)
        val location: Location? = null
        val capabilities = this.cookingCapabilities.filterNotNull()

        val id = null

        val team = Team(id, cook1, cook2, address, diet, capabilities, location, city, null)

        return team
    }
}