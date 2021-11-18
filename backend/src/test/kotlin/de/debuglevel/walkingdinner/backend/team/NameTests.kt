package de.debuglevel.walkingdinner.backend.team


import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NameTests {
    @ParameterizedTest
    @MethodSource("nameProvider")
    fun getAbbreviatedName(testData: NameData) {
        // Arrange

        // Act
        val name = Name(firstname = testData.firstname, lastname = testData.lastname)

        // Assert
        Assertions.assertThat(name.abbreviatedName).isEqualTo(testData.expectedAbbreviatedName)
    }

    fun nameProvider() = Stream.of(
        NameData(
            firstname = "John",
            lastname = "Dorian",
            expectedAbbreviatedName = "J. Dorian"
        ),
        NameData(
            firstname = "Jennifer Dylan",
            lastname = "Cox",
            expectedAbbreviatedName = "J. Cox"
        ),
        NameData(
            firstname = "Robert-Bob",
            lastname = "Kelso",
            expectedAbbreviatedName = "R. Kelso"
        )
    )

    data class NameData(
        val firstname: String,
        val lastname: String,
        val expectedAbbreviatedName: String
    )
}