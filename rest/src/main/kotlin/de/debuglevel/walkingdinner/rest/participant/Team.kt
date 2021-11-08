package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.dinner.Dinner
import de.debuglevel.walkingdinner.rest.participant.location.Location
import java.util.*
import javax.persistence.*

@Entity
data class Team(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val cook1: Cook,

    @OneToOne(cascade = [CascadeType.ALL])
    val cook2: Cook,

    val address: String,

    @Enumerated(EnumType.STRING)
    val diet: Diet,

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val cookingCapabilities: List<CookingCapability>,

    @OneToOne(cascade = [CascadeType.ALL])
    var location: Location?,

    val city: String,

    // TODO: is nullable because the CSV importer would need a Dinner otherwise... can be non-null if importer is removed.
    @ManyToOne
    val dinner: Dinner?
) {
    override fun toString(): String {
        return "Team(id=$id)"
    }

    val cooks
        get() = setOf(cook1, cook2)
}