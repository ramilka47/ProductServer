package com.flower.server.database.dao

import com.flower.server.database.models.storage.ProductCountEntity
import com.flower.server.database.models.storage.StorageOperationEntity
import com.flower.server.database.models.storage.StorageOperationEnum
import com.flower.server.database.models.storage.StorageProductDataEntity

interface IStockroomDao : CountDao, StorageOperationDao, StorageProductDataDao

interface CountDao{

    suspend fun addCount(productId : Long) : ProductCountEntity?

    suspend fun updateCount(productId: Long, count : Int) : Boolean

    suspend fun getCount(productId: Long) : ProductCountEntity?

}

interface StorageOperationDao{

    suspend fun addBuy(productId : Long,
               count : Int,
               price : Double,
               date : Long) : StorageOperationEntity?

    suspend fun addSale(productId : Long,
                count : Int,
                price : Double,
                date : Long) : StorageOperationEntity?

    suspend fun addWriteOff(productId : Long,
                            count : Int,
                            price : Double,
                            date : Long) : StorageOperationEntity?

    suspend fun addReturn(productId : Long,
                          count : Int,
                          price : Double,
                          date : Long) : StorageOperationEntity?

    suspend fun addReserve(productId : Long,
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

    suspend fun getAllOperationsByIds(ids : List<Long>) : List<StorageOperationEntity>

}

interface StorageProductDataDao{

    suspend fun addStorageProductData(productId : Long,
                              price : Double,
                              discount : Float,
                              uniCode : String) : StorageProductDataEntity?

    suspend fun updateStorageProductData(productId : Long,
                                 price : Double? = null,
                                 discount : Float? = null,
                                 uniCode : String? = null) : Boolean

    suspend fun getStorageProductData(productId: Long) : StorageProductDataEntity?

    suspend fun getStorageProductData(uniCode: String) : StorageProductDataEntity?

}