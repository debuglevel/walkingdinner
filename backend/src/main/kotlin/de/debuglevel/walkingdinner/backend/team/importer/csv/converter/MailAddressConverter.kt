package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.common.MailAddress

class MailAddressConverter<T, I> : AbstractBeanField<T, I>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): MailAddress {
        return MailAddress(value = value)
    }
}
