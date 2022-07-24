package cavalier.dev.routes

import cavalier.dev.persistence.dao.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.gameRouting(gameService: GameService) {
    route("/game") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val game = gameService.game(id.toInt()) ?: return@get call.respondText(
                "No game found with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(game)
        }
    }
}