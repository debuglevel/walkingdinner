package de.debuglevel.walkingdinner.backend.common

import com.google.i18n.phonenumbers.PhoneNumberUtil
import mu.KotlinLogging

object PhoneNumberUtils {
    private val logger = KotlinLogging.logger {}

    /**
     * Formats a phone number
     */
    fun format(phoneNumber: String): String {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val numberProto = phoneUtil.parse(phoneNumber, "DE")
        return phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    }
}