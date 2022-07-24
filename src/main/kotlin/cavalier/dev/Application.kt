package cavalier.dev

import cavalier.dev.persistence.dao.DatabaseFactory
import cavalier.dev.persistence.dao.gameService
import cavalier.dev.plugins.configureRouting
import cavalier.dev.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        watchPaths = listOf("classes")
    ) {
        DatabaseFactory.init()
        configureSerialization()
        configureRouting(gameService)
    }.start(wait = true)
}
