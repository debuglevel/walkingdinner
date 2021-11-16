package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.team.Name

/**
 * Converts a String to a [Name].
 */
class NameConverter<T, I> : AbstractBeanField<T, I>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Name {
        return convertValue(value)
    }

    /**
     * Convert a [nameString] to a [Name]
     * @implNote Separate public function to being able to test it.
     */
    fun convertValue(nameString: String): Name {
        return Name(name = nameString)
    }
}
