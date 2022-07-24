package cavalier.dev.persistence.dao

import cavalier.dev.persistence.Games
import cavalier.dev.persistence.GamesAndPublishers
import cavalier.dev.persistence.Publishers
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcUrl = "jdbc:sqlite:./data/data.db"
        val database = Database.connect(jdbcUrl, driverClassName)
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(
                Games,
                Publishers,
                GamesAndPublishers
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}