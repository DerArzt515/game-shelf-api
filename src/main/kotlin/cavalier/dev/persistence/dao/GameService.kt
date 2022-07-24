package cavalier.dev.persistence.dao

import Game
import MinMax
import cavalier.dev.persistence.Games
import cavalier.dev.persistence.Publishers
import cavalier.dev.persistence.dao.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class GameService : DataService<Game> {
    private fun resultRowToGame(row: ResultRow) = Game(
        title = row[Games.title],
        playerCount = MinMax(
            min = row[Games.minPlayerCount],
            max = row[Games.maxPlayerCount]
        ),
        playTime = MinMax(
            min = row[Games.minPlayTime],
            max = row[Games.maxPlayTime]
        ),
        publishers = listOf(),
        designers = listOf()
    )

    override suspend fun getAll(): List<Game> = dbQuery {
        Games.selectAll().map(::resultRowToGame)
    }

    override suspend fun getBy(id: Int): Game? = dbQuery {
        Games
            .select { Games.id eq id }
            .map(::resultRowToGame)
            .singleOrNull()
    }

    override suspend fun addNew(game: Game): Game? = dbQuery {
        val insertStatement = Games.insert {
            it[title] = game.title
            it[minPlayerCount] = game.playerCount.min
            it[maxPlayerCount] = game.playerCount.max
            it[minPlayTime] = game.playTime.min
            it[maxPlayTime] = game.playTime.max
        }
        val publishers = Publishers.batchInsert(
            data = game.publishers,
            shouldReturnGeneratedValues = true,
        ) {
            this[Publishers.name] = it
        }


        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToGame)

    }

    override suspend fun update(id: Int, game: Game): Boolean = dbQuery {
        Games.update({ Games.id eq id }) {
            it[title] = game.title
            it[minPlayerCount] = game.playerCount.min
            it[maxPlayerCount] = game.playerCount.max
            it[minPlayTime] = game.playTime.min
            it[maxPlayTime] = game.playTime.max
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Games.deleteWhere { Games.id eq id } > 0
    }
}
