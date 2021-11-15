package de.debuglevel.walkingdinner.backend.course

class Courses {
    // TODO: should be labeled in english
    // TODO: should be an enum
    companion object {
        const val course1name = "Vorspeise"
        const val course2name = "Hauptspeise"
        const val course3name = "Dessert"
        val coursesNames = listOf(course1name, course2name, course3name)
    }
}