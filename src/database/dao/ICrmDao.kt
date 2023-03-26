package com.flower.server.database.dao

import com.flower.server.database.models.crm.CustomerEntity
import com.flower.server.database.models.crm.RelationshipCustomerToOperationEntity

interface ICrmDao : CustomerDao, RelationshipCustomerToOperationDao

interface CustomerDao{

    suspend fun addCustomer(name : String? = null, phone : String? = null, address : String? = null, scores : Int? = null) : CustomerEntity?

    suspend fun updateCustomer(id : Long, name : String? = null, phone : String? = null, address : String? = null, scores : Int? = null) : Boolean

    suspend fun getCustomer(id : Long) : CustomerEntity?

    suspend fun getAllCustomer() : List<CustomerEntity>

}

interface RelationshipCustomerToOperationDao{

    suspend fun addCustomerToOperation(customerId : Long, operationId : Long) : RelationshipCustomerToOperationEntity?

    suspend fun getCustomerToOperation(id : Long) : RelationshipCustomerToOperationEntity?

    suspend fun getAllCustomerToOperation() : List<RelationshipCustomerToOperationEntity>

    suspend fun getAllCustomerToOperationByCustomerId(customerId : Long) : List<RelationshipCustomerToOperationEntity>
}

