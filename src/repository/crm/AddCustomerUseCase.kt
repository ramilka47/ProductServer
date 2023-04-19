package com.flower.server.repository.crm

import com.flower.server.database.dao.CustomerDao
import com.flower.server.web.models.response.crm.Customer
import com.flower.server.web.models.response.crm.toCustomer
import java.rmi.ServerException

class AddCustomerUseCase(private val customerDao : CustomerDao) {

    suspend fun execute(name : String, phone : String? = null, address : String? = null) : Customer =
        customerDao.addCustomer(name = name, phone = phone, address = address)?.toCustomer()
            ?: throw ServerException("can't add customer")
}