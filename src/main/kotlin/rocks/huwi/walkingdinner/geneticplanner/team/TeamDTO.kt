package rocks.huwi.walkingdinner.geneticplanner.team

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import rocks.huwi.walkingdinner.geneticplanner.dietcompatibility.Capability


class TeamDTO {
    @CsvCustomBindByName(column = "Koch1", converter = Name.ConvertName::class)
    lateinit var name1: Name

    @CsvCustomBindByName(column = "Koch2", converter = Name.ConvertName::class)
    lateinit var name2: Name

    @CsvCustomBindByName(column = "Telefon1", converter = PhoneNumber.ConvertPhoneNumber::class)
    lateinit var phone1: PhoneNumber

    @CsvCustomBindByName(column = "Telefon2", converter = PhoneNumber.ConvertPhoneNumber::class)
    lateinit var phone2: PhoneNumber

    @CsvCustomBindByName(column = "Mail1", converter = MailAddress.ConvertMailAddress::class)
    lateinit var mail1: MailAddress

    @CsvCustomBindByName(column = "Mail2", converter = MailAddress.ConvertMailAddress::class)
    lateinit var mail2: MailAddress

    @CsvBindByName(column = "Adresse")
    lateinit var address: String

    @CsvBindByName(column = "Diet")
    lateinit var diet: String

    @CsvCustomBindByName(column = "Capabilities", converter = Capability.ConvertCapabilities::class)
    val capabilities: List<Capability?> = listOf()

    fun toTeam(): Team {
        val cook1 = Cook(name1, mail1, phone1)
        val cook2 = Cook(name2, mail2, phone2)
        val location = null
        val capabilities = this.capabilities.filterNotNull()
        val team = Team(cook1, cook2, address, diet, capabilities, location)

        return team
    }
}