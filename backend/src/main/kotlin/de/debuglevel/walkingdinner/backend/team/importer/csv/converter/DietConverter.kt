package de.debuglevel.walkingdinner.backend.team.importer.csv.converter

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvDataTypeMismatchException
import de.debuglevel.walkingdinner.backend.team.Diet

/**
 * Converts a String to a [Diet].
 */
class DietConverter<T, I> : AbstractBeanField<T, I>() {
    @Throws(CsvDataTypeMismatchException::class)
    override fun convert(value: String): Diet {
        return convertValue(value)
    }

    /**
     * Convert a [dietString] to a [Diet]
     * @implNote Separate public function to being able to test it.
     */
    fun convertValue(dietString: String): Diet {
        return Diet.valueOf(dietString)
    }
}
