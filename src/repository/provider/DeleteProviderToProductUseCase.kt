package com.flower.server.repository.provider

import com.flower.server.database.dao.ProviderToProductDao
import com.flower.server.helper.execeptions.RequestException

class DeleteProviderToProductUseCase(private val providerToProductDao : ProviderToProductDao) {

    suspend fun execute(id : Long) =
        providerToProductDao.deleteProviderToProduct(id).apply {
            if (!this){
                throw RequestException("bad relationship provider to product id=$id")
            }
        }
}