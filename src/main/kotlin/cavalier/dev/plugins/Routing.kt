package cavalier.dev.plugins

import cavalier.dev.routes.gameRouting
import cavalier.dev.services.GameService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(gameService: GameService) {
    routing {
        gameRouting(gameService)
    }
}
