package cavalier.dev.routes

import cavalier.dev.services.GameService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.gameRouting(gameService: GameService) {
    route("/game") {
        get("{id?}") {
            call.respond(
                gameService.get(1)
            )
        }

    }
}