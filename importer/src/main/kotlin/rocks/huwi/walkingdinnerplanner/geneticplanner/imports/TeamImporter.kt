package rocks.huwi.walkingdinnerplanner.geneticplanner.imports

import rocks.huwi.walkingdinnerplanner.model.team.Team

interface TeamImporter {
    fun import(): List<Team>
}