package de.debuglevel.walkingdinner.backend.participant.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.participant.CookingCapability
import mu.KotlinLogging

class CapabilitiesConverter<T> : AbstractBeanField<T>() {
    private val logger = KotlinLogging.logger {}

    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        logger.trace { "Converting capability answers '$value' to capability enums..." }

        val answers = value.split(';')

        val capabilities = mapOf<String, CookingCapability>(
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
            .map { capabilities[it] }

        logger.trace { "Converted capability answers '$value' to capability enums: ${teamCapabilities.joinToString(",")}" }

        return teamCapabilities
    }

    @Throws(CsvDataTypeMismatchException::class)
    override fun convertToWrite(value: Any?): String {
        TODO("Serialization form object to CSV is not implemented")
    }
}