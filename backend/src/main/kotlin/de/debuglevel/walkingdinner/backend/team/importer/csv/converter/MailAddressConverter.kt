package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.common.MailAddress

class MailAddressConverter<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return MailAddress(value = value)
    }
}