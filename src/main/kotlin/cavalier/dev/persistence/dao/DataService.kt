package cavalier.dev.persistence.dao

import Game
import MinMax
import kotlinx.coroutines.runBlocking

interface DataService<T> {
    suspend fun getAll(): List<T>
    suspend fun getBy(id: Int): T?
    suspend fun addNew(record: T): T?
    suspend fun update(id: Int, record: T): Boolean
    suspend fun delete(id: Int): Boolean
}

private val game = Game(
    title = "Uno",
    playerCount = MinMax(
        min = 2,
        max = 4
    ),
    playTime = MinMax(
        min = 15,
        max = 30
    ),
    publishers = listOf("MB"),
    designers = listOf("Someone")
)

val gameService: GameService = GameService().apply {
    runBlocking {
        if (getAll().isEmpty()) {
            addNew(game)
        }
    }
}
