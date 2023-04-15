package com.flower.server.database.dao.impl

import com.flower.server.database.dao.IBonusClubDao
import com.flower.server.database.models.bonus_club.BonusCardEntity
import com.flower.server.database.models.bonus_club.BonusCardTable
import com.flower.server.database.models.bonus_club.BonusCountEntity
import com.flower.server.database.models.bonus_club.BonusCountTable
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class BonusClubDaoImpl : IBonusClubDao {

    override suspend fun addBonusCard(name: String, active: Boolean): BonusCardEntity? = dbQuery {
        val statement = BonusCardTable.insert {
            it[BonusCardTable.name] = name
            it[BonusCardTable.active] = active
        }

        statement.resultedValues?.singleOrNull()?.let { resultToBonusCard(it) }
    }

    override suspend fun addBonusCount(bonusCardId: Long, bonusCount: Double): BonusCountEntity? = dbQuery {
        val statement = BonusCountTable.insert {
            it[BonusCountTable.bonusCardId] = bonusCardId
            it[BonusCountTable.bonus] = bonusCount
        }

        statement.resultedValues?.singleOrNull()?.let { resultToBonusCount(it) }
    }

    override suspend fun updateBonusCard(id: Long, name: String?, active: Boolean?): Boolean = dbQuery {
        BonusCardTable.update({ BonusCardTable.id eq id }) {statement->
            name?.let { statement[BonusCardTable.name] = it }
            active?.let { statement[BonusCardTable.active] = it }
        } > 0
    }

    override suspend fun updateBonusCount(bonusCardId: Long, bonusCount: Double): Boolean = dbQuery {
        BonusCountTable.update({ BonusCountTable.bonusCardId eq bonusCardId }) {statement->
            bonusCount?.let { statement[BonusCountTable.bonus] = it }
        } > 0
    }

    override suspend fun deleteBonusCard(id: Long): Boolean = dbQuery {
        BonusCardTable.deleteWhere { BonusCardTable.id eq id } > 0
    }

    override suspend fun deleteBonusCount(bonusCardId: Long): Boolean = dbQuery {
        BonusCountTable.deleteWhere { BonusCountTable.bonusCardId eq bonusCardId } > 0
    }

    override suspend fun getBonusCard(id: Long): BonusCardEntity? = dbQuery {
        BonusCardTable.select { BonusCardTable.id eq id }.singleOrNull()?.let { resultToBonusCard(it) }
    }

    override suspend fun getBonusCount(bonusCardId: Long): BonusCountEntity? = dbQuery {
        BonusCountTable.select { BonusCountTable.bonusCardId eq bonusCardId }.singleOrNull()?.let { resultToBonusCount(it) }
    }

    override suspend fun getAllBonusCards(): List<BonusCardEntity> = dbQuery {
        BonusCardTable.selectAll().map { resultToBonusCard(it) }
    }

    private fun resultToBonusCard(row: ResultRow) = BonusCardEntity(
        id = row[BonusCardTable.id],
        name = row[BonusCardTable.name],
        active = row[BonusCardTable.active]
    )

    private fun resultToBonusCount(row: ResultRow) = BonusCountEntity(
        bonusCardId = row[BonusCountTable.bonusCardId],
        bonus = row[BonusCountTable.bonus]
    )

}