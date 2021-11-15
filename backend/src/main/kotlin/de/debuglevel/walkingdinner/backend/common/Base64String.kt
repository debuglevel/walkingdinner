package de.debuglevel.walkingdinner.backend.common

import java.util.*

/**
 * Class to hold a Base64 encoded value. Primarily to decode it easily.
 */
data class Base64String(val base64encodedValue: String) {
    /**
     * Decode the Base64 encoded value (which should probably better be plaintext) into a String.
     */
    val decodedString: String
        get() {
            return String(Base64.getDecoder().decode(base64encodedValue))
        }
}