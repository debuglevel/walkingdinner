package de.debuglevel.walkingdinner.backend.team.importer.csv

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import de.debuglevel.walkingdinner.backend.team.*
import de.debuglevel.walkingdinner.backend.team.importer.csv.converter.*

class TeamDTO {
    @CsvCustomBindByName(column = "Koch1", converter = NameConverter::class)
    lateinit var cook1name: Name

    @CsvCustomBindByName(column = "Koch2", converter = NameConverter::class)
    lateinit var cook2name: Name

    @CsvCustomBindByName(column = "Telefon1", converter = PhoneNumberConverter::class)
    lateinit var cook1phone: PhoneNumber

    @CsvCustomBindByName(column = "Telefon2", converter = PhoneNumberConverter::class)
    lateinit var cook2phone: PhoneNumber

    @CsvCustomBindByName(column = "Mail1", converter = MailAddressConverter::class)
    lateinit var cook1mail: MailAddress

    @CsvCustomBindByName(column = "Mail2", converter = MailAddressConverter::class)
    lateinit var cook2mail: MailAddress

    @CsvBindByName(column = "Adresse")
    lateinit var address: String

    @CsvBindByName(column = "Stadt")
    lateinit var city: String

    @CsvCustomBindByName(column = "Diet", converter = DietConverter::class)
    lateinit var diet: Diet

    @CsvCustomBindByName(column = "Capabilities", converter = CookingCapabilitiesConverter::class)
    val cookingCapabilities: List<CookingCapability?> = listOf()

    fun toTeam(): Team {
        val cook1 = Cook(null, cook1name, cook1mail, cook1phone)
        val cook2 = Cook(null, cook2name, cook2mail, cook2phone)
        val capabilities = this.cookingCapabilities.filterNotNull()

        val team = Team(null, cook1, cook2, address, diet, capabilities, null, city, null)
        return team
    }
}