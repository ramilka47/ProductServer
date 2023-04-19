package com.flower.server.repository.provider

import com.flower.server.database.dao.ProviderToProductDao
import java.rmi.ServerException

class AddProviderToProductUseCase(private val providerToProductDao : ProviderToProductDao) {

    suspend fun execute(productId : Long, providerId : Long) =
        providerToProductDao.addProviderToProduct(productId, providerId)
            ?: throw ServerException("can't add relationship provider and product")
}