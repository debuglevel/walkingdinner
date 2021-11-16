package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PhoneNumberConverterTests {
    @Test
    fun `string is converted`() {
        // Arrange
        val phoneNumberString = "1234"

        // Act
        val phoneNumber = PhoneNumberConverter<String, String>().convertValue(phoneNumberString)

        // Assert
        Assertions.assertThat(phoneNumber.number).isEqualTo(phoneNumberString)
    }
}