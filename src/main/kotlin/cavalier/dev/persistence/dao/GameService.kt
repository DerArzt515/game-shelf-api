package cavalier.dev.persistence.dao


import Game
import GameCreationRequest
import cavalier.dev.persistence.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.ktorm.entity.find

class GameService(private val database: Database) : DataService<GameCreationRequest, Game> {

    override suspend fun getAll(): List<Game> {
//        return database.gameDbos.toList().map { it.convert() }
        return database
                .from(GameDbos)
                .select()
                .map {
                    GameDbos.createEntity(it)
                }.map { it.convert() }
    }

    override suspend fun getBy(id: Int): Game? {
        return database
                .from(GameDbos)
                .select()
                .where(GameDbos.id eq id)
                .limit(1)
                .map { GameDbos.createEntity(it) }
                .map { it.convert() }
                .first()
    }

    override suspend fun addNew(record: GameCreationRequest): Game? {
        val toInsert = GameDbo {
            title = record.title
            minPlayerCount = record.playerCount.min
            maxPlayerCount = record.playerCount.max
            minPlayTime = record.playTime.min
            maxPlayTime = record.playTime.max
        }
        database.gameDbos.add(toInsert)
//        val publishers = upsertPublishers()
//        createRelationship(toInsert.id, record.publishers)
        return toInsert.convert()
    }

    private fun createRelationship(gameId: Int, publishers: List<String>) {
        upsertPublishers(publishers).forEach { publisherId ->
            database.insertAndGenerateKey(GamesToPublishers) {
                set(it.gameId, gameId)
                set(it.publisherId, publisherId)
            }
        }
    }

//    fun link(gameId: Int, publisherId: Int): Int {
//
//    }

    private fun upsertPublishers(publishers: List<String>): List<Int> {
        return publishers.map { publisher ->
            val id: Int? = database.from(PublisherDbos)
                .select(PublisherDbos.id)
                .where(PublisherDbos.name eq publisher)
                .map { row -> row[PublisherDbos.id] }
                .firstOrNull()
            id ?: run {
                database.insertAndGenerateKey(PublisherDbos) {
                    set(it.name, publisher)
                } as Int
//                database.insert
            }
        }
    }


    override suspend fun update(record: Game): Boolean {
        val id = record.id ?: throw RuntimeException()
        val rec = database.gameDbos.find { it.id eq id } ?: return false
        rec.title = record.title
        rec.minPlayerCount = record.playerCount.min
        rec.maxPlayerCount = record.playerCount.max
        rec.minPlayTime = record.playTime.min
        rec.maxPlayTime = record.playTime.max
        return rec.flushChanges() > 0
    }
    override suspend fun delete(id: Int): Boolean {
        return database.gameDbos.find { it.id eq id }?.let {
            it.delete()
            true
        } ?: false
    }
}
