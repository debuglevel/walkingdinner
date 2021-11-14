package de.debuglevel.walkingdinner.planner.common.geo

import de.debuglevel.walkingdinner.planner.Location
import java.lang.Math.toRadians
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

object SimpleVincentyDistanceCalculator : DistanceCalculator {
    /**
     * Calculates the distance (in kilometers) between to [Location]s according to the Haversine formula.
     * @implNote based on https://stackoverflow.com/a/27943/4764279
     */
    override fun calculate(
        source: Location,
        destination: Location
    ): Double {
        return calculate(source.latitude, source.longitude, destination.latitude, destination.longitude) / 1000.0
    }

    /**
     * Calculates geodetic distance between two points specified by latitude/longitude using Vincenty inverse formula
     * for ellipsoids
     * from https://stackoverflow.com/a/9822531/4764279
     *
     * @param lat1
     * first point latitude in decimal degrees
     * @param lon1
     * first point longitude in decimal degrees
     * @param lat2
     * second point latitude in decimal degrees
     * @param lon2
     * second point longitude in decimal degrees
     * @returns distance in meters between points with 5.10<sup>-4</sup> precision
     * @see [Originally posted here](http://www.movable-type.co.uk/scripts/latlong-vincenty.html)
     */
    fun calculate(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val a = 6378137.0
        val b = 6356752.314245
        val f = 1 / 298.257223563 // WGS-84 ellipsoid params
        val L = Math.toRadians(lon2 - lon1)
        val U1 = atan((1 - f) * kotlin.math.tan(toRadians(lat1)))
        val U2 = atan((1 - f) * tan(Math.toRadians(lat2)))
        val sinU1 = sin(U1)
        val cosU1 = cos(U1)
        val sinU2 = sin(U2)
        val cosU2 = cos(U2)
        var sinLambda: Double
        var cosLambda: Double
        var sinSigma: Double
        var cosSigma: Double
        var sigma: Double
        var sinAlpha: Double
        var cosSqAlpha: Double
        var cos2SigmaM: Double
        var lambda = L
        var lambdaP: Double
        var iterLimit = 100.0
        do {
            sinLambda = Math.sin(lambda)
            cosLambda = Math.cos(lambda)
            sinSigma = Math.sqrt(
                cosU2 * sinLambda * (cosU2 * sinLambda)
                        + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
            )
            if (sinSigma == 0.0) return 0.0 // co-incident points
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda
            sigma = Math.atan2(sinSigma, cosSigma)
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma
            cosSqAlpha = 1 - sinAlpha * sinAlpha
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha
            if (java.lang.Double.isNaN(cos2SigmaM)) cos2SigmaM = 0.0 // equatorial line: cosSqAlpha=0 (§6)
            val C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha))
            lambdaP = lambda
            lambda = L + ((1 - C) * f * sinAlpha
                    * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM))))
        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0)
        if (iterLimit == 0.0) return Double.NaN // formula failed to converge
        val uSq = cosSqAlpha * (a * a - b * b) / (b * b)
        val A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)))
        val B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)))
        val deltaSigma = (B
                * sinSigma
                * (cos2SigmaM + B
                / 4
                * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - (B / 6 * cos2SigmaM
                * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)))))
        return b * A * (sigma - deltaSigma)
    }
}