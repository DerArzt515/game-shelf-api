package cavalier.dev.persistence.dao

import org.ktorm.database.Database

typealias Password = String
typealias Username = String
typealias Url = String

object DatabaseFactory {
    fun init(jdbcUrl: String, username: Username, password: Password): Database {
        val db = Database.connect(
                jdbcUrl,
                user = username,
                password = password
        )
        return db
    }
}