package com.flower.server.repository.provider

import com.flower.server.database.dao.ProviderToProductDao

class GetAllProviderToProductByProviderIdUseCase(private val providerToProductDao : ProviderToProductDao) {

    suspend fun execute(providerId : Long) =
        providerToProductDao.getAllProviderToProductByProviderId(providerId)
}