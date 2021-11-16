package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.team.CookingCapability
import mu.KotlinLogging

/**
 * Converts a String to a List of [CookingCapability]s.
 */
class CapabilitiesConverter<T, I> : AbstractBeanField<T, I>() {
    private val logger = KotlinLogging.logger {}

    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): List<CookingCapability> {
        return convertValue(value)
    }

    /**
     * Convert a [cookingCapabilitiesList] to [CookingCapability]s
     * @implNote Separate public function to being able to test it.
     */
    fun convertValue(cookingCapabilitiesList: String): List<CookingCapability> {
        logger.trace { "Converting capability answers '$cookingCapabilitiesList' to capability enums..." }

        val answers = cookingCapabilitiesList.split(';')

        val capabilities = mapOf(
            "Ich schaffe es, eine vegane Vorspeise zu machen." to CookingCapability.VeganAppetizer,
            "Ich schaffe es, ein veganes Hauptgericht zu machen." to CookingCapability.VeganMaindish,
            "Ich schaffe es, ein veganes Dessert zu machen." to CookingCapability.VeganDessert,
            "Ich schaffe es, eine vegetarische Vorspeise zu machen." to CookingCapability.VegetarianAppetizer,
            "Ich schaffe es, ein vegetarisches Hauptgericht zu machen." to CookingCapability.VegetarianMaindish,
            "Ich schaffe es, ein vegetarisches Dessert zu machen." to CookingCapability.VegetarianDessert,
            "Ich schaffe es, eine omnivore Vorspeise zu machen." to CookingCapability.OmnivoreAppetizer,
            "Ich schaffe es, ein omnivores Hauptgericht zu machen." to CookingCapability.OmnivoreMaindish,
            "Ich schaffe es, ein omnivores Dessert zu machen." to CookingCapability.OmnivoreDessert
        )

        val teamCapabilities = answers
            //.onEach { logger.trace {"'${capabilities[it]}' derived from '$it'"} }
            .onEach {
                if (!capabilities.contains(it)) {
                    logger.error("Could not map answer '$it' to capability enum.")
                }
            }
            .map { capabilities.getValue(it) }

        logger.trace {
            "Converted capability answers '$cookingCapabilitiesList' to capability enums: ${
                teamCapabilities.joinToString(
                    ","
                )
            }"
        }

        return teamCapabilities
    }

    @Throws(CsvDataTypeMismatchException::class)
    override fun convertToWrite(value: Any?): String {
        TODO("Serialization form object to CSV is not implemented")
    }
}