package de.debuglevel.walkingdinner.planner.common

import de.debuglevel.walkingdinner.planner.location.Location
import de.debuglevel.walkingdinner.planner.common.geo.GeoUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeoUtilsTests {

    @ParameterizedTest
    @MethodSource("locationProvider")
    fun `calculate distance`(testData: LocationDistanceData) {
        // Arrange

        // Act
        val distance1to2 = GeoUtils.cachedCalculateDistance(testData.location1, testData.location2)
        val distance2to1 = GeoUtils.cachedCalculateDistance(testData.location2, testData.location1)

        // Assert
        assertThat(distance1to2).isCloseTo(testData.distance, within(0.01))
        assertThat(distance2to1).isEqualTo(testData.distance, within(0.01))
    }

    fun locationProvider() = Stream.of(
        LocationDistanceData(
            Location(10.883799, 49.895679),
            Location(10.883799, 49.895679),
            0.0
        ),
        LocationDistanceData(
            Location(10.883799, 49.895679),
            Location(10.885178, 49.889114),
            0.74
        )
    )

    data class LocationDistanceData(
        val location1: Location,
        val location2: Location,
        val distance: Double
    )
}