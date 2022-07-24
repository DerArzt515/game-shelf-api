package cavalier.dev.services

import Game
import MinMax

class GameService {
    fun get(id: Int) = Game(
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
}