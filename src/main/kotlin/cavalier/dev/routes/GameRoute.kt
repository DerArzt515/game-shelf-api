package cavalier.dev.routes

import Game
import cavalier.dev.persistence.dao.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.gameRouting(gameService: GameService) {
    route("/game") {
        get() {
            call.respond(gameService.getAll())
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val game = gameService.getBy(id.toInt()) ?: return@get call.respondText(
                "No game found with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(game)
        }
        post{
            val game = call.receive<Game>()
            val savedGame = gameService.addNew(game)?: return@post call.respondText(
                "error saving game",
                status = HttpStatusCode.InternalServerError
            )
            call.respond(savedGame)
        }
    }
}