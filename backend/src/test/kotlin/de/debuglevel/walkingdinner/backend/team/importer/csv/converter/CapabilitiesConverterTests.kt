package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import de.debuglevel.walkingdinner.backend.team.CookingCapability
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CapabilitiesConverterTests {
    @Test
    fun `string is converted`() {
        // Arrange
        val cookingCapabilitiesList =
            """Ich schaffe es, eine vegane Vorspeise zu machen.;Ich schaffe es, ein veganes Hauptgericht zu machen.;Ich schaffe es, ein veganes Dessert zu machen.;Ich schaffe es, eine vegetarische Vorspeise zu machen.;Ich schaffe es, ein vegetarisches Hauptgericht zu machen.;Ich schaffe es, ein vegetarisches Dessert zu machen.;Ich schaffe es, eine omnivore Vorspeise zu machen.;Ich schaffe es, ein omnivores Hauptgericht zu machen.;Ich schaffe es, ein omnivores Dessert zu machen.""".trimIndent()

        // Act
        val cookingCapabilities = CapabilitiesConverter<String, String>().convertValue(cookingCapabilitiesList)

        // Assert
        Assertions.assertThat(cookingCapabilities).containsAll(CookingCapability.values().toList())
    }
}