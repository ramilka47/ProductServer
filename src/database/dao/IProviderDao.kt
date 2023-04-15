package com.flower.server.database.dao

import com.flower.server.database.models.provider.ProviderEntity
import com.flower.server.database.models.provider.ProviderToProductEntity

interface IProviderDao : ProviderDao, ProviderToProductDao

interface ProviderDao{

    suspend fun addProvider(name : String,
                    address : String,
                    description : String,
                    phone : String) : ProviderEntity?

    suspend fun updateProvider(id : Long,
                       name : String? = null,
                       address : String? = null,
                       description : String? = null,
                       phone : String? = null) : Boolean

    suspend fun deleteProvider(id : Long) : Boolean

    suspend fun getAllProvider() : List<ProviderEntity>

    suspend fun getProvider(id : Long) : ProviderEntity?
}

interface ProviderToProductDao{

    suspend fun addProviderToProduct(productId: Long, providerId : Long) : ProviderToProductEntity?

    suspend fun deleteProviderToProduct(id : Long) : Boolean

    suspend fun deleteProviderToProductByProviderId(providerId : Long) : Boolean

    suspend fun getAllProviderToProduct() : List<ProviderToProductEntity>

    suspend fun getAllProviderToProductByProductId(productId : Long) : List<ProviderToProductEntity>

    suspend fun getAllProviderToProductByProviderId(providerId : Long) : List<ProviderToProductEntity>
}