package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.dinner.DinnerService
import io.micronaut.context.BeanContext
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class TeamService(
    private val teamRepository: TeamRepository,
    private val beanContext: BeanContext,
) {
    private val logger = KotlinLogging.logger {}

    /**
     * @implNote In case [DinnerService] also wants to access [TeamService],
     *  this allows a circular dependency as they are not set during initialization.
     */
    private val dinnerService: DinnerService
        get() = beanContext.getBean(DinnerService::class.java)

    fun get(id: UUID): Team {
        logger.debug { "Getting team '$id'..." }
        val team = teamRepository.findById(id).orElseThrow { TeamNotFoundException(id) }
        logger.debug { "Got team: $team" }
        return team
    }

    fun getAll(): Set<Team> {
        logger.debug { "Getting all teams..." }
        val teams = teamRepository.findAll().toSet()
        logger.debug { "Got all teams" }
        return teams
    }

    @Transactional
    open fun save(team: Team): Team {
        logger.debug { "Saving team '$team'..." }
        // Load organisation again inside this transaction; would throw an LazyInitializationException otherwise
        val dinner = dinnerService.get(team.dinner?.id!!)
        val savingTeam = team.copy(dinner = dinner)
        val savedTeam = teamRepository.save(savingTeam)
        logger.debug { "Saved team: $savedTeam" }
        return savedTeam
    }

    class TeamNotFoundException(teamId: UUID) : Exception("Team $teamId not found")
}