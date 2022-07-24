package cavalier.dev.plugins

import cavalier.dev.persistence.dao.GameService
import cavalier.dev.routes.gameRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(gameService: GameService) {
    routing {
        gameRouting(gameService)
    }
}
