package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NameConverterTests {
    @Test
    fun `string is converted`() {
        // Arrange
        val nameString = "Sherlock Holmes"

        // Act
        val name = NameConverter<String, String>().convertValue(nameString)

        // Assert
        Assertions.assertThat(name.name).isEqualTo(nameString)
    }
}