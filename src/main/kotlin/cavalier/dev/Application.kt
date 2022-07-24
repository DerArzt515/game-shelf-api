package cavalier.dev

import cavalier.dev.plugins.configureRouting
import cavalier.dev.plugins.configureSerialization
import cavalier.dev.services.GameService
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        watchPaths = listOf("classes")
    ) {
        val gameService = GameService()
        configureSerialization()
        configureRouting(gameService)
    }.start(wait = true)
}
