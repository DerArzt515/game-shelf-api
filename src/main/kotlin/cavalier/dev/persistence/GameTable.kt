package cavalier.dev.persistence

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


object Games : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val title: Column<String> = varchar("title", 256)
    val minPlayerCount: Column<Int> = integer("minPlayerCount")
    val maxPlayerCount: Column<Int> = integer("maxPlayerCount")
    val minPlayTime: Column<Int> = integer("minPlayTime")
    val maxPlayTime: Column<Int> = integer("maxPlayTime")

    override val primaryKey = PrimaryKey(id, name = "gameId")
}

object Publishers : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 256)

    override val primaryKey = PrimaryKey(id, name = "publisherId")
}

object GamePublishers : Table() {
    var game = reference("game", Games).primaryKey(0)
    var publisher = reference("publisher", Publishers)
}
