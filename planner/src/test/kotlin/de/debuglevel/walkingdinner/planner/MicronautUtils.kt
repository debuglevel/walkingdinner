package de.debuglevel.walkingdinner.planner

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer

object MicronautUtils {
    fun getPort(applicationContext: ApplicationContext): Int {
        return applicationContext.getBean(EmbeddedServer::class.java).port
    }
}