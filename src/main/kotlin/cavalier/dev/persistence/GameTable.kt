package cavalier.dev.persistence

import Game
import MinMax
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface GameRecord : Entity<GameRecord> {
    companion object : Entity.Factory<GameRecord>()

    val id: Int
    var title: String
    var minPlayerCount: Int
    var maxPlayerCount: Int
    var minPlayTime: Int
    var maxPlayTime: Int

    fun convert() = Game(
        id = this.id,
        title = this.title,
        playerCount = MinMax(this.minPlayerCount, this.maxPlayerCount),
        playTime = MinMax(this.minPlayTime, this.maxPlayTime),
        publishers = listOf(),
        designers = listOf()
    )
}

object GameRecords : Table<GameRecord>("games") {
    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val minPlayerCount = int("minPlayerCount").bindTo { it.minPlayerCount }
    val maxPlayerCount = int("maxPlayerCount").bindTo { it.maxPlayerCount }
    val minPlayTime = int("minPlayTime").bindTo { it.minPlayTime }
    val maxPlayTime = int("maxPlayTime").bindTo { it.maxPlayTime }

}

val Database.gameRecords get() = this.sequenceOf(GameRecords)

//interface Publisher : Entity<Publisher> {
//    companion object : Entity.Factory<Publisher>()
//
//    val id: Int
//    val name: String
//}
//
//object Publishers : Table<Publisher>("publishers") {
//    val id = int("id").primaryKey().bindTo { it.id }
//    val name = varchar("name").bindTo { it.name }
//}
//
//interface GameToPublisher : Entity<GameToPublisher> {
////    companion object : Entity.Factory<GameToPublisher>()
//
//    val game: Game
//    val publisher: Publisher
//}
//
//object GamesToPublishers : Table<GameToPublisher>("gamesToPublishers") {
//    val gameId = int("gameId").references(Games) { it.game }
//    val publisher = int("publisherId").references(Publishers) { it.publisher }
//}