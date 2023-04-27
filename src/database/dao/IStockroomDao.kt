package com.flower.server.database.dao

import com.flower.server.database.models.storage.*

interface IStockroomDao : CountDao, StorageOperationDao, StorageProductDataDao, OperationPositionDao

interface CountDao{

    suspend fun addCount(productId : Long) : ProductCountEntity?

    suspend fun updateCount(productId: Long, count : Int) : Boolean

    suspend fun getCount(productId: Long) : ProductCountEntity?

    suspend fun getCount(productIds : List<Long>) : List<ProductCountEntity>
}

interface StorageOperationDao{

    suspend fun addBuy(operationPositions : List<Long>,
                       count : Int,
                       price : Double,
                       date : Long) : StorageOperationEntity?

    suspend fun addSale(operationPositions : List<Long>,
                        count : Int,
                        price : Double,
                        date : Long) : StorageOperationEntity?

    suspend fun addWriteOff(operationPositions : List<Long>,
                            count : Int,
                            price : Double,
                            date : Long) : StorageOperationEntity?

    suspend fun addReturn(operationPositions : List<Long>,
                          count : Int,
                          price : Double,
                          date : Long) : StorageOperationEntity?

    suspend fun addReserve(operationPositions : List<Long>,
                           count : Int,
                           price : Double,
                           date : Long) : StorageOperationEntity?

    suspend fun updateOperation(id : Long, operation : StorageOperationEnum) : Boolean

    suspend fun deleteOperation(id : Long) : Boolean

    suspend fun getAllOperation() : List<StorageOperationEntity>

    suspend fun getAllBought() : List<StorageOperationEntity>

    suspend fun getAllSales() : List<StorageOperationEntity>

    suspend fun getAllWriteOffs() : List<StorageOperationEntity>

    suspend fun getAllReturned() : List<StorageOperationEntity>

    suspend fun getAllReserved() : List<StorageOperationEntity>

    suspend fun getOperation(id : Long) : StorageOperationEntity?

    suspend fun getAllOperationsByIds(ids : List<Long>) : List<StorageOperationEntity>

}

interface OperationPositionDao{

    suspend fun addOperationPosition(productId: Long, count : Int) : OperationPositionEntity?

    suspend fun deleteOperationPosition(id : Long) : Boolean

    suspend fun updateOperationPosition(id : Long, productId: Long? = null, count: Int? = null) : Boolean

    suspend fun getAllOperationPosition() : List<OperationPositionEntity>

    suspend fun getAllOperationPosition(ids : List<Long>) : List<OperationPositionEntity>
}

interface StorageProductDataDao{

    suspend fun addStorageProductData(productId : Long,
                                      price : Double,
                                      salePrice : Double,
                                      discount : Float,
                                      uniCode : String) : StorageProductDataEntity?

    suspend fun updateStorageProductData(productId : Long,
                                         price : Double? = null,
                                         salePrice : Double? = null,
                                         discount : Float? = null,
                                         uniCode : String? = null) : Boolean

    suspend fun getStorageProductData(productIds : List<Long>) : List<StorageProductDataEntity>

    suspend fun getStorageProductData(productId: Long) : StorageProductDataEntity?

    suspend fun getStorageProductData(uniCode: String) : StorageProductDataEntity?

}