package com.flower.server.repository.provider

import com.flower.server.database.dao.ProviderToProductDao

class GetAllProviderToProductUseCase(private val providerToProductDao : ProviderToProductDao) {

    suspend fun execute() =
        providerToProductDao.getAllProviderToProduct()
}