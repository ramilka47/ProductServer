package com.flower.server.database.dao.impl

import com.flower.server.database.dao.IProviderDao
import com.flower.server.database.models.provider.ProviderEntity
import com.flower.server.database.models.provider.ProviderTable
import com.flower.server.database.models.provider.ProviderToProductEntity
import com.flower.server.database.models.provider.ProviderToProductTable
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProviderDaoImpl : IProviderDao {

    override suspend fun addProvider(name: String, address: String, description: String, phone: String): ProviderEntity? = dbQuery {
        val insertStatement = ProviderTable.insert {
            it[ProviderTable.name] = name
            it[ProviderTable.address] = address
            it[ProviderTable.description] = description
            it[ProviderTable.phone] = phone
        }

        insertStatement.resultedValues?.singleOrNull()?.let { resultToProvider(it) }
    }

    override suspend fun addProviderToProduct(productId: Long, providerId: Long): ProviderToProductEntity? = dbQuery {
        val insertStatement = ProviderToProductTable.insert {
            it[ProviderToProductTable.providerId] = providerId
            it[ProviderToProductTable.productId] = productId
        }

        insertStatement.resultedValues?.singleOrNull()?.let { resultToProviderToProduct(it) }
    }

    override suspend fun updateProvider(id: Long, name: String?, address: String?, description: String?, phone: String?): Boolean = dbQuery {
        ProviderTable.update {statement->
            name?.let { statement[ProviderTable.name] = it }
            address?.let { statement[ProviderTable.address] = it }
            description?.let { statement[ProviderTable.description] = it }
            phone?.let { statement[ProviderTable.phone] = it }
        } > 0
    }

    override suspend fun deleteProvider(id: Long): Boolean = dbQuery {
        ProviderTable.deleteWhere { ProviderTable.id eq id } > 0
    }

    override suspend fun deleteProviderToProduct(id: Long): Boolean = dbQuery {
        ProviderToProductTable.deleteWhere { ProviderToProductTable.id eq id } > 0
    }

    override suspend fun deleteProviderToProductByProviderId(providerId: Long): Boolean = dbQuery {
        ProviderToProductTable.deleteWhere { ProviderToProductTable.providerId eq providerId } > 0
    }

    override suspend fun getProvider(id: Long): ProviderEntity? = dbQuery {
        ProviderTable.select { ProviderTable.id eq id }.singleOrNull()?.let { resultToProvider(it) }
    }

    override suspend fun getAllProvider(): List<ProviderEntity> = dbQuery {
        ProviderTable.selectAll().map { resultToProvider(it) }
    }

    override suspend fun getAllProviderToProduct(): List<ProviderToProductEntity> = dbQuery {
        ProviderToProductTable.selectAll().map { resultToProviderToProduct(it) }
    }

    override suspend fun getAllProviderToProductByProductId(productId: Long): List<ProviderToProductEntity> = dbQuery {
        ProviderToProductTable.select { ProviderToProductTable.productId eq productId }.map { resultToProviderToProduct(it) }
    }

    override suspend fun getAllProviderToProductByProviderId(providerId: Long): List<ProviderToProductEntity> = dbQuery {
        ProviderToProductTable.select { ProviderToProductTable.providerId eq providerId }.map { resultToProviderToProduct(it) }
    }

    private suspend fun resultToProvider(row : ResultRow) = ProviderEntity(
        id = row[ProviderTable.id],
        name = row[ProviderTable.name],
        address = row[ProviderTable.address],
        description = row[ProviderTable.description],
        phone = row[ProviderTable.phone])

    private suspend fun resultToProviderToProduct(row: ResultRow) = ProviderToProductEntity(
        id = row[ProviderToProductTable.id],
        providerId = row[ProviderToProductTable.providerId],
        productId = row[ProviderToProductTable.productId]
        )

}