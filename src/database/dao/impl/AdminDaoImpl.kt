package com.flower.server.database.dao.impl

import com.flower.server.database.dao.IAdminDao
import com.flower.server.database.models.admin.UserEntity
import com.flower.server.database.models.admin.UserTable
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AdminDaoImpl : IAdminDao {

    override suspend fun addUser(
        name: String,
        login: String,
        password: String,
        level: Int,
        token: String
    ): UserEntity? = dbQuery {
        val insertStatement = UserTable.insert { statement->
            statement[UserTable.name] = name
            statement[UserTable.login] = login
            statement[UserTable.password] = password
            statement[UserTable.level] = level
            statement[UserTable.token] = token
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToUser)
    }

    override suspend fun updateUser(
        id: Long,
        name: String?,
        login: String?,
        password: String?,
        level: Int?,
        token: String?
    ): Boolean = dbQuery {
        UserTable.update({ UserTable.id eq id }) { statement ->
            name?.let { statement[UserTable.name] = it }
            login?.let { statement[UserTable.login] = it }
            password?.let { statement[UserTable.password] = it }
            level?.let { statement[UserTable.level] = level }
            token?.let { statement[UserTable.token] = token }
        } > 0
    }

    override suspend fun deleteUser(id : Long): Boolean = dbQuery {
        UserTable.deleteWhere { UserTable.id eq id } > 0
    }

    override suspend fun getUser(id: Long): UserEntity? = dbQuery {
        UserTable.select { UserTable.id eq id }.singleOrNull()?.let { resultToUser(it) }
    }

    override suspend fun getUserByName(name: String): UserEntity? = dbQuery {
        UserTable.select { UserTable.name eq name }.singleOrNull()?.let { resultToUser(it) }
    }

    override suspend fun getUserByLogin(login: String): UserEntity? = dbQuery {
        UserTable.select { UserTable.login eq login }.singleOrNull()?.let { resultToUser(it) }
    }

    override suspend fun getUserByToken(token: String): UserEntity? = dbQuery {
        UserTable.select { UserTable.token eq token }.singleOrNull()?.let { resultToUser(it) }
    }

    override suspend fun getAllUsers(): List<UserEntity> = dbQuery {
        UserTable.selectAll().map(::resultToUser)
    }

    private fun resultToUser(row: ResultRow) = UserEntity(
        id = row[UserTable.id],
        name = row[UserTable.name],
        login = row[UserTable.login],
        password = row[UserTable.password],
        level = row[UserTable.level],
        token = row[UserTable.token]
    )

}