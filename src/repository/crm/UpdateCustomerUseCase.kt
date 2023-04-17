package com.flower.server.repository.crm

import com.flower.server.database.dao.CustomerDao
import java.rmi.ServerException

class UpdateCustomerUseCase(private val customerDao : CustomerDao) {

    suspend fun execute(id : Long, name : String? = null, phone : String? = null, address : String? = null) : Boolean =
        customerDao.updateCustomer(
            id = id,
            name = name,
            phone = phone,
            address = address
        ).apply {
            if (!this && customerDao.getCustomer(id) != null)
                throw ServerException("can't update customer")
        }
}