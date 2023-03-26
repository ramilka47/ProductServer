package com.flower.server.database.dao.impl

import com.flower.server.database.dao.IStockroomDao
import com.flower.server.database.models.storage.*
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StockroomDaoImpl : IStockroomDao {

    override suspend fun addCount(productId: Long) = dbQuery {
        val insertStatement = ProductCountTable.insert { statement->
            statement[ProductCountTable.productId] = productId
            statement[ProductCountTable.count] = 0
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToCount)
    }

    override suspend fun updateCount(productId: Long, count: Int): Boolean = dbQuery {
        ProductCountTable.update({ ProductCountTable.productId eq productId }){ statement->
            statement[ProductCountTable.count] = count
        } > 0
    }

    override suspend fun getCount(productId: Long): ProductCountEntity? = dbQuery {
        ProductCountTable.select { ProductCountTable.productId eq productId }.singleOrNull()?.let{ resultToCount(it) }
    }

    private suspend fun addOperation(productId: Long, count: Int, operation : StorageOperationEnum, price: Double, date: Long) = dbQuery {
        val insertStatement = StorageOperationTable.insert { statement->
            statement[StorageOperationTable.productId] = productId
            statement[StorageOperationTable.count] = count
            statement[StorageOperationTable.operation] = operation.name
            statement[StorageOperationTable.price] = price
            statement[StorageOperationTable.date] = date
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToStorageOperation)
    }

    override suspend fun addBuy(productId: Long, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(productId, count, StorageOperationEnum.BUY, price, date)

    override suspend fun addSale(productId: Long, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(productId, count, StorageOperationEnum.SALE, price, date)

    override suspend fun addWriteOff(productId: Long, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(productId, count, StorageOperationEnum.WRITE_OFF, price, date)

    override suspend fun addReturn(productId: Long, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(productId, count, StorageOperationEnum.RETURN, price, date)

    override suspend fun addReserve(productId: Long, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(productId, count, StorageOperationEnum.RESERVE, price, date)

    override suspend fun deleteOperation(id: Long): Boolean = dbQuery {
        StorageOperationTable.deleteWhere { StorageOperationTable.id eq id } > 0
    }

    override suspend fun getAllOperation(): List<StorageOperationEntity> = dbQuery {
        StorageOperationTable.selectAll().map(::resultToStorageOperation)
    }

    private suspend fun getAllOperation(operation: StorageOperationEnum): List<StorageOperationEntity> = dbQuery {
        StorageOperationTable.select { StorageOperationTable.operation eq operation.name }.map(::resultToStorageOperation)
    }

    override suspend fun getAllBought(): List<StorageOperationEntity> =
        getAllOperation(StorageOperationEnum.BUY)

    override suspend fun getAllSales(): List<StorageOperationEntity> =
        getAllOperation(StorageOperationEnum.SALE)

    override suspend fun getAllWriteOffs(): List<StorageOperationEntity> =
        getAllOperation(StorageOperationEnum.WRITE_OFF)

    override suspend fun getAllReturned(): List<StorageOperationEntity> =
        getAllOperation(StorageOperationEnum.RETURN)

    override suspend fun getAllReserved(): List<StorageOperationEntity> =
        getAllOperation(StorageOperationEnum.RESERVE)

    override suspend fun getAllOperationsByIds(ids: List<Long>): List<StorageOperationEntity> = dbQuery {
        StorageOperationTable.select { StorageOperationTable.id inList ids }.map(::resultToStorageOperation)
    }

    override suspend fun addStorageProductData(
        productId: Long,
        price: Double,
        discount: Float,
        uniCode: String
    ): StorageProductDataEntity? = dbQuery {
        val insertStatement = StorageProductDataTable.insert { statement->
            statement[StorageProductDataTable.productId] = productId
            statement[StorageProductDataTable.price] = price
            statement[StorageProductDataTable.discount] = discount
            statement[StorageProductDataTable.uniCode] = uniCode
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToStorageProductData)
    }

    override suspend fun updateStorageProductData(
        productId: Long,
        price: Double?,
        discount: Float?,
        uniCode: String?
    ): Boolean = dbQuery {
        StorageProductDataTable.update({ StorageProductDataTable.productId eq productId }){statement->
            price?.let { statement[StorageProductDataTable.price] = it }
            discount?.let { statement[StorageProductDataTable.discount] = it }
            uniCode?.let { statement[StorageProductDataTable.uniCode] = it }
        } > 0
    }

    override suspend fun getStorageProductData(productId: Long): StorageProductDataEntity? = dbQuery {
        StorageProductDataTable.select { StorageProductDataTable.productId eq productId }.singleOrNull()?.let { resultToStorageProductData(it) }
    }

    override suspend fun getStorageProductData(uniCode: String): StorageProductDataEntity? = dbQuery {
        StorageProductDataTable.select { StorageProductDataTable.uniCode eq uniCode }.singleOrNull()?.let { resultToStorageProductData(it) }
    }

    private fun resultToCount(row : ResultRow) = ProductCountEntity(
        productId = row[ProductCountTable.productId],
        count = row[ProductCountTable.count]
    )

    private fun resultToStorageOperation(row: ResultRow) = StorageOperationEntity(
        id = row[StorageOperationTable.id],
        productId = row[StorageOperationTable.productId],
        operation = row[StorageOperationTable.operation],
        count = row[StorageOperationTable.count],
        price = row[StorageOperationTable.price],
        date = row[StorageOperationTable.date]
    )

    private fun resultToStorageProductData(row: ResultRow) = StorageProductDataEntity(
        productId = row[StorageProductDataTable.productId],
        price = row[StorageProductDataTable.price],
        discount = row[StorageProductDataTable.discount],
        uniCode = row[StorageProductDataTable.uniCode]
    )

}