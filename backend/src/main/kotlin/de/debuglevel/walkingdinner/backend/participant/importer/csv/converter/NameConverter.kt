package de.debuglevel.walkingdinner.backend.participant.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.participant.Name

class NameConverter<T> : AbstractBeanField<T>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Any? {
        return Name(name = value)
    }
}
