package cavalier.dev.persistence.dao

import kotlinx.coroutines.Dispatchers
import org.ktorm.database.Database
import org.ktorm.support.sqlite.SQLiteDialect

object DatabaseFactory {
    fun init(): Database {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcUrl = "jdbc:sqlite:./data/data.db"
        val db = Database.connect(jdbcUrl, driverClassName, dialect = SQLiteDialect())
        return db
    }
}