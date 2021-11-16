package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.common.MailAddress

/**
 * Converts a String to a [MailAddress].
 */
class MailAddressConverter<T, I> : AbstractBeanField<T, I>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): MailAddress {
        return convertValue(value)
    }

    /**
     * Convert a [mailAddressString] to a [MailAddress]
     * @implNote Separate public function to being able to test it.
     */
    fun convertValue(mailAddressString: String): MailAddress {
        return MailAddress(value = mailAddressString)
    }
}
