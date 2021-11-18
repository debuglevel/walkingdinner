package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NameConverterTests {
    @Test
    fun `string is converted`() {
        // Arrange

        // Act
        val name = NameConverter<String, String>().convertValue("Harry James Potter")

        // Assert
        Assertions.assertThat(name.firstname).isEqualTo("Harry James")
        Assertions.assertThat(name.lastname).isEqualTo("Potter")
    }
}