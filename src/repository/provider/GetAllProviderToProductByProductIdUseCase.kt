package com.flower.server.repository.provider

import com.flower.server.database.dao.ProviderToProductDao

class GetAllProviderToProductByProductIdUseCase(private val providerToProductDao : ProviderToProductDao) {

    suspend fun execute(productId : Long) =
        providerToProductDao.getAllProviderToProductByProductId(productId)
}