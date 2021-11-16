package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.common.PhoneNumber

/**
 * Converts a String to a [PhoneNumber].
 */
class PhoneNumberConverter<T, I> : AbstractBeanField<T, I>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): PhoneNumber {
        return convertValue(value)
    }

    /**
     * Convert a [phoneNumberString] to a [PhoneNumber]
     * @implNote Separate public function to being able to test it.
     */
    fun convertValue(phoneNumberString: String): PhoneNumber {
        return PhoneNumber(number = phoneNumberString)
    }
}
