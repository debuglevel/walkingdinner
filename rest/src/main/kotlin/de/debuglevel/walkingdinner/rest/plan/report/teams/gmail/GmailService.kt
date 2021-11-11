package de.debuglevel.walkingdinner.rest.plan.report.teams.gmail

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.Draft
import com.google.api.services.gmail.model.Message
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailService
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Path
import javax.mail.internet.MimeMessage

// see: https://developers.google.com/gmail/api/SendEmail.java
@Singleton
class GmailService(
    @Property(name = "app.walkingdinner.data.base-path") private val dataBasepath: Path,
    @Property(name = "app.walkingdinner.reporters.gmail.credentials-folder") private val credentialsFolder: Path,
    @Property(name = "app.walkingdinner.reporters.gmail.client-secrets-file") private val clientSecretsFile: String,
    private val mailService: MailService
) {
    private val logger = KotlinLogging.logger {}

    private val APPLICATION_NAME = "Walking Dinner"
    private val jacksonFactory = JacksonFactory.getDefaultInstance()

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved `credentials/` folder.
     */
    private val authorizationScopes = listOf(GmailScopes.GMAIL_COMPOSE)

    private val gmail: Gmail

    init {
        logger.debug { "Initializing Gmail service..." }

        // Build a new authorized API client service.
        val netHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credentials = getCredentials(netHttpTransport)

        gmail = Gmail
            .Builder(netHttpTransport, jacksonFactory, credentials)
            .setApplicationName(APPLICATION_NAME)
            .build()

        logger.debug { "Initialized Gmail service..." }
    }

    /**
     * Creates an authorized Credential object. Saves it in a data store.
     */
    private fun getCredentials(netHttpTransport: NetHttpTransport): Credential {
        logger.debug { "Getting credentials..." }

        val clientSecrets = getClientSecrets()

        val credentialsFolderFile = dataBasepath.resolve(credentialsFolder).toFile()

        // Build flow and trigger user authorization request.
        logger.trace { "Building authorization code flow..." }
        val authorizationCodeFlow = GoogleAuthorizationCodeFlow
            .Builder(netHttpTransport, jacksonFactory, clientSecrets, authorizationScopes)
            .setDataStoreFactory(FileDataStoreFactory(credentialsFolderFile))
            .setAccessType("offline")
            .build()

        logger.trace { "Authorizing google user..." }
        val credential = AuthorizationCodeInstalledApp(authorizationCodeFlow, LocalServerReceiver())
            .authorize("user")

        logger.debug { "Got credentials" }
        return credential
    }

    /**
     * Gets the client secrets (i.e. API key and stuff for this application) from the [clientSecretsFile] JSON file.
     */
    @Throws(IOException::class)
    private fun getClientSecrets(): GoogleClientSecrets {
        logger.debug { "Getting client secrets..." }

        val reader = try {
            logger.trace { "Creating input stream reader for '$clientSecretsFile'..." }
            InputStreamReader(Gmail::class.java.getResourceAsStream(clientSecretsFile))
        } catch (e: Exception) {
            logger.error(e) { "Could not read Google Gmail client secrets from '$clientSecretsFile'" }
            throw e
        }

        logger.trace { "Loading client secrets..." }
        val clientSecrets = GoogleClientSecrets.load(jacksonFactory, reader)

        logger.debug { "Got client secrets" }
        return clientSecrets
    }

    /**
     * Creates and saves a draft mail.
     */
    fun saveDraft(toMailAddresses: Set<String>, subject: String, bodyText: String): Draft {
        logger.debug { "Saving draft for '$toMailAddresses'..." }

        // "me" indicates that the authorized user should be used
        val authorizedUserValue = "me"
        val mimeMessage = mailService.buildMimeMessage(toMailAddresses, authorizedUserValue, subject, bodyText)
        val gmailMessage = buildMessage(mimeMessage)
        val draft = createDraft(authorizedUserValue, gmailMessage)

        logger.debug { "Saved draft '${draft.id}'" }
        return draft
    }

    /**
     * Creates a draft from the [message]. The [userId] should usually be "me" to reference the authenticated user.
     */
    private fun createDraft(
        userId: String,
        message: Message
    ): Draft {
        logger.debug { "Creating draft on Gmail..." }

        val draft = Draft().apply { this.message = message }

        // TODO: this could better be a BatchRequest
        logger.trace { "Submitting draft to Gmail..." }
        val submittedDraft = gmail.users().drafts().create(userId, draft).execute()
        logger.trace { "Submitted draft to Gmail" }

        logger.debug { "Created draft on Gmail '${submittedDraft.id}'" }
        logger.trace { "Created draft on Gmail '${submittedDraft.id}': ${submittedDraft.toPrettyString()}" }
        return submittedDraft
    }

    /**
     * Creates a Gmail [Message] from a [MimeMessage].
     */
    private fun buildMessage(mimeMessage: MimeMessage): Message {
        logger.trace { "Building message from mime message..." }

        val byteArrayOutputStream = ByteArrayOutputStream()
        mimeMessage.writeTo(byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        val encodedEmail = Base64.encodeBase64URLSafeString(bytes)

        val message = Message().apply { raw = encodedEmail }

        logger.trace { "Built message from mime message" }
        return message
    }
}