package de.debuglevel.walkingdinner.rest.plan.report.teams.mail

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Singleton
class MailService {
    private val logger = KotlinLogging.logger {}

    /**
     * Creates a [MimeMessage] from the [fromMailAddress] to all [toMailAddresses] with a [subject] and a [bodyText].
     */
    fun buildMimeMessage(
        toMailAddresses: Set<String>,
        fromMailAddress: String,
        subject: String,
        bodyText: String
    ): MimeMessage {
        logger.trace { "Creating mime message..." }

        val properties = Properties()
        val session = Session.getDefaultInstance(properties, null)

        val mimeMessage = MimeMessage(session)

        // Set "from" header
        mimeMessage.setFrom(InternetAddress(fromMailAddress))

        // Set "to" headers
        for (toMailAddress in toMailAddresses) {
            mimeMessage.addRecipient(
                javax.mail.Message.RecipientType.TO,
                InternetAddress(toMailAddress)
            )
        }

        // Set subject
        mimeMessage.subject = subject

        // Set text
        mimeMessage.setText(bodyText)

        logger.trace { "Created mime message" }
        return mimeMessage
    }
}