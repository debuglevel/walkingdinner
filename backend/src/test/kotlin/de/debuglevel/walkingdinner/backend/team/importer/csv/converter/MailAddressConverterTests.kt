package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MailAddressConverterTests {
    @Test
    fun `string is converted`() {
        // Arrange
        val mailAddressString = "sherlock.holmes@invalid.invalid"

        // Act
        val mailAddress = MailAddressConverter<String, String>().convertValue(mailAddressString)

        // Assert
        Assertions.assertThat(mailAddress.value).isEqualTo(mailAddressString)
    }
}