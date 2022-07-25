package cavalier.dev.persistence.dao


import Game
import cavalier.dev.persistence.GameRecord
import cavalier.dev.persistence.gameRecords
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.toList

class GameService(private val database: Database) : DataService<Game> {

    override suspend fun getAll(): List<Game> {
        return database.gameRecords.toList().map { it.convert() }
    }

    override suspend fun getBy(id: Int): Game? {
        return database.gameRecords.find { it.id eq id }?.convert()
    }

    override suspend fun addNew(game: Game): Game? {
        val toInsert = GameRecord{
            title = game.title
            minPlayerCount = game.playerCount.min
            maxPlayerCount = game.playerCount.max
            minPlayTime = game.playTime.min
            maxPlayTime = game.playTime.max
        }
        val id = database.gameRecords.add(toInsert)
        return toInsert.convert()
    }

    override suspend fun update(game: Game): Boolean {
        val id = game.id?: throw RuntimeException()
        val rec = database.gameRecords.find { it.id eq id } ?: return false
        rec.title = game.title
        rec.minPlayerCount = game.playerCount.min
        rec.maxPlayerCount = game.playerCount.max
        rec.minPlayTime = game.playTime.min
        rec.maxPlayTime = game.playTime.max
        return rec.flushChanges() > 0
    }

    override suspend fun delete(id: Int): Boolean {
        return database.gameRecords.find { it.id eq id }?.let {
            it.delete()
            true
        } ?: false
    }
}
