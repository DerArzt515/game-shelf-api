package cavalier.dev.persistence

import Game
import MinMax
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

interface GameDbo : Entity<GameDbo> {
    companion object : Entity.Factory<GameDbo>()

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

object GameDbos : Table<GameDbo>("games") {
    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val minPlayerCount = int("min_player_count").bindTo { it.minPlayerCount }
    val maxPlayerCount = int("max_player_count").bindTo { it.maxPlayerCount }
    val minPlayTime = int("min_play_time").bindTo { it.minPlayTime }
    val maxPlayTime = int("max_play_time").bindTo { it.maxPlayTime }
}

val Database.gameDbos get() = this.sequenceOf(GameDbos)
val Database.publisherDbos get() = this.sequenceOf(PublisherDbos)

interface PublisherDbo : Entity<PublisherDbo> {
    companion object : Entity.Factory<PublisherDbo>()

    val id: Int
    var name: String
}

object PublisherDbos : Table<PublisherDbo>("publishers") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
}

interface GameToPublisher : Entity<GameToPublisher> {
    companion object : Entity.Factory<GameToPublisher>()

    val game: GameDbo
    val publisher: PublisherDbo
}

object GamesToPublishers : Table<GameToPublisher>("game_to_publisher") {
    val gameId = int("game_id").references(GameDbos) { it.game}
    val publisherId = int("publisher_id").references(PublisherDbos) { it.publisher }
}