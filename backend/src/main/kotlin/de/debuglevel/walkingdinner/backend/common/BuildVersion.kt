package de.debuglevel.walkingdinner.backend.common

object BuildVersion {
    val buildVersion: String
        get() {
            return BuildVersion::class.java.`package`.implementationVersion ?: "<unknown version>"
        }

    val buildTitle: String
        get() {
            return BuildVersion::class.java.`package`.implementationTitle ?: "<unknown title>"
        }
}