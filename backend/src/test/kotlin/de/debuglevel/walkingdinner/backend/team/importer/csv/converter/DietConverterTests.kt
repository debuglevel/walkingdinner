package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DietConverterTests {
    @ParameterizedTest
    @ValueSource(strings = ["Vegan", "Vegetarian", "Omnivore"])
    fun `string is converted`(value: String) {
        // Arrange
        val dietString = value

        // Act
        val diet = DietConverter<String, String>().convertValue(dietString)

        // Assert
        Assertions.assertThat(diet.name).isEqualTo(dietString)
    }
}