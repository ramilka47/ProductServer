package com.flower.server.database.dao.impl

import com.flower.server.database.converter.ConvertListLong
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

    override suspend fun getCount(productIds: List<Long>): List<ProductCountEntity> = dbQuery {
        ProductCountTable.select { ProductCountTable.productId inList productIds }.map(::resultToCount)
    }

    override suspend fun addOperationPosition(productId: Long, count: Int): OperationPositionEntity? = dbQuery {
        val insertStatement = OperationPositionTable.insert { statement->
            statement[OperationPositionTable.productId] = productId
            statement[OperationPositionTable.count] = count
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToSimpleOperation)
    }

    private suspend fun addOperation(operationPositions : List<Long>, count: Int, operation : StorageOperationEnum, price: Double, date: Long) = dbQuery {
        val insertStatement = StorageOperationTable.insert { statement->
            statement[StorageOperationTable.operationPositions] = ConvertListLong.toString(operationPositions)
            statement[StorageOperationTable.count] = count
            statement[StorageOperationTable.operation] = operation.name
            statement[StorageOperationTable.price] = price
            statement[StorageOperationTable.date] = date
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToStorageOperation)
    }

    override suspend fun addBuy(operationPositions : List<Long>, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(operationPositions, count, StorageOperationEnum.BUY, price, date)

    override suspend fun addSale(operationPositions : List<Long>, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(operationPositions, count, StorageOperationEnum.SALE, price, date)

    override suspend fun addWriteOff(operationPositions : List<Long>, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(operationPositions, count, StorageOperationEnum.WRITE_OFF, price, date)

    override suspend fun addReturn(operationPositions : List<Long>, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(operationPositions, count, StorageOperationEnum.RETURN, price, date)

    override suspend fun addReserve(operationPositions : List<Long>, count: Int, price: Double, date: Long): StorageOperationEntity? =
        addOperation(operationPositions, count, StorageOperationEnum.RESERVE, price, date)

    override suspend fun deleteOperation(id: Long): Boolean = dbQuery {
        StorageOperationTable.deleteWhere { StorageOperationTable.id eq id } > 0
    }

    override suspend fun deleteOperationPosition(id: Long): Boolean = dbQuery {
        OperationPositionTable.deleteWhere { OperationPositionTable.id eq id } > 0
    }

    override suspend fun getAllOperation(): List<StorageOperationEntity> = dbQuery {
        StorageOperationTable.selectAll().map(::resultToStorageOperation)
    }

    private suspend fun getAllOperation(operation: StorageOperationEnum): List<StorageOperationEntity> = dbQuery {
        StorageOperationTable.select { StorageOperationTable.operation eq operation.name }.map(::resultToStorageOperation)
    }

    override suspend fun getAllOperationPosition(): List<OperationPositionEntity> = dbQuery {
        OperationPositionTable.selectAll().map(::resultToSimpleOperation)
    }

    override suspend fun getAllOperationPosition(ids: List<Long>): List<OperationPositionEntity> = dbQuery {
        OperationPositionTable.select { OperationPositionTable.id inList ids }.map(::resultToSimpleOperation)
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

    override suspend fun getOperation(id: Long): StorageOperationEntity? = dbQuery {
        StorageOperationTable.select { StorageOperationTable.id eq id }.singleOrNull()?.let(::resultToStorageOperation)
    }

    override suspend fun getAllOperationsByIds(ids: List<Long>): List<StorageOperationEntity> = dbQuery {
        StorageOperationTable.select { StorageOperationTable.id inList ids }.map(::resultToStorageOperation)
    }

    override suspend fun addStorageProductData(
        productId: Long,
        price: Double,
        salePrice : Double,
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

    override suspend fun updateOperationPosition(id: Long, productId: Long?, count: Int?): Boolean = dbQuery {
        OperationPositionTable.update({ OperationPositionTable.id eq id}) { statement->
            productId?.let { statement[OperationPositionTable.productId] = it }
            count?.let { statement[OperationPositionTable.count] = it }
        } > 0
    }

    override suspend fun updateStorageProductData(
        productId: Long,
        price: Double?,
        salePrice : Double?,
        discount: Float?,
        uniCode: String?
    ): Boolean = dbQuery {
        StorageProductDataTable.update({ StorageProductDataTable.productId eq productId }){statement->
            price?.let { statement[StorageProductDataTable.price] = it }
            salePrice?.let { statement[StorageProductDataTable.salePrice] = it }
            discount?.let { statement[StorageProductDataTable.discount] = it }
            uniCode?.let { statement[StorageProductDataTable.uniCode] = it }
        } > 0
    }

    override suspend fun updateOperation(id: Long, operation: StorageOperationEnum): Boolean = dbQuery {
        StorageOperationTable.update({ StorageOperationTable.id eq id}){ statement->
            statement[StorageOperationTable.operation] = operation.name
        } > 0
    }

    override suspend fun getStorageProductData(productIds: List<Long>): List<StorageProductDataEntity> = dbQuery {
        StorageProductDataTable.select { StorageProductDataTable.productId inList productIds }.map(::resultToStorageProductData)
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
        operationPositions = ConvertListLong.toList(row[StorageOperationTable.operationPositions]),
        operation = StorageOperationEnum.valueOf(row[StorageOperationTable.operation]),
        count = row[StorageOperationTable.count],
        price = row[StorageOperationTable.price],
        date = row[StorageOperationTable.date]
    )

    private fun resultToSimpleOperation(row: ResultRow) = OperationPositionEntity(
        id = row[OperationPositionTable.id],
        productId = row[OperationPositionTable.productId],
        count = row[OperationPositionTable.count]
    )

    private fun resultToStorageProductData(row: ResultRow) = StorageProductDataEntity(
        productId = row[StorageProductDataTable.productId],
        price = row[StorageProductDataTable.price],
        salePrice = row[StorageProductDataTable.salePrice],
        discount = row[StorageProductDataTable.discount],
        uniCode = row[StorageProductDataTable.uniCode]
    )

}