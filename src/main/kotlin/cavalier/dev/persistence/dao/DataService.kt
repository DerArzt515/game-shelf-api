package cavalier.dev.persistence.dao

import Game
import MinMax
import kotlinx.coroutines.runBlocking

interface DataService<PartialType, FullType> {
    suspend fun getAll(): List<FullType>
    suspend fun getBy(id: Int): FullType?
    suspend fun addNew(record: PartialType): FullType?
    suspend fun update(record: FullType): Boolean
    suspend fun delete(id: Int): Boolean
}

//private val game = Game(
//    id = 1,
//    title = "Uno",
//    playerCount = MinMax(
//        min = 2,
//        max = 4
//    ),
//    playTime = MinMax(
//        min = 15,
//        max = 30
//    ),
//    publishers = listOf("MB"),
//    designers = listOf("Someone")
//)

//val gameService: GameService = GameService().apply {
//    runBlocking {
//        if (getAll().isEmpty()) {
//            addNew(game)
//        }
//    }
//}
