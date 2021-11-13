package de.debuglevel.walkingdinner.backend.participant

import de.debuglevel.walkingdinner.backend.dinner.Dinner
import de.debuglevel.walkingdinner.backend.participant.location.Location
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Team(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
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

    val cooks
        get() = setOf(cook1, cook2)

    override fun toString(): String {
        return "Team(" +
                "id=$id, " +
                "address='$address', " +
                "city='$city'" +
                ")"
    }
}