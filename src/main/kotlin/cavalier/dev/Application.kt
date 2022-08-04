package cavalier.dev

import cavalier.dev.persistence.dao.DatabaseFactory
import cavalier.dev.persistence.dao.GameService
import cavalier.dev.persistence.dao.Url
import cavalier.dev.plugins.configureRouting
import cavalier.dev.plugins.configureSerialization
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        watchPaths = listOf("classes")
    ) {
        val jdbcUrl: Url = "jdbc:postgresql://localhost:5432/game-shelf"
        val username = "username"
        val password = "pass"
        val db = DatabaseFactory.init(jdbcUrl, username, password)
        val gameService = GameService(db)
        configureSerialization()
        configureRouting(gameService)
    }.start(wait = true)
}
