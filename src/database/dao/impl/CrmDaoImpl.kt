package com.flower.server.database.dao.impl

import com.flower.server.database.dao.ICrmDao
import com.flower.server.database.models.crm.CustomerEntity
import com.flower.server.database.models.crm.CustomerTable
import com.flower.server.database.models.crm.RelationshipCustomerToOperationEntity
import com.flower.server.database.models.crm.RelationshipCustomerToOperationTable
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CrmDaoImpl : ICrmDao {

    override suspend fun addCustomer(name: String?, phone: String?, address: String?, scores: Int?): CustomerEntity? = dbQuery {
        val insertStatement = CustomerTable.insert { statement->
            statement[CustomerTable.name] = name
            statement[CustomerTable.phone] = phone
            statement[CustomerTable.address] = address
            statement[CustomerTable.scores] = scores
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultCustomer)
    }

    override suspend fun updateCustomer(
        id: Long,
        name: String?,
        phone: String?,
        address: String?,
        scores: Int?
    ): Boolean = dbQuery {
        CustomerTable.update({ CustomerTable.id eq id }){ statement->
            name?.let { statement[CustomerTable.name] = name }
            phone?.let { statement[CustomerTable.phone] = phone }
            address?.let { statement[CustomerTable.address] = address }
            scores?.let { statement[CustomerTable.scores] = scores }
        } > 0
    }

    override suspend fun getCustomer(id: Long): CustomerEntity? = dbQuery { 
        CustomerTable.select { CustomerTable.id eq id }.singleOrNull()?.let { resultCustomer(it) }
    }

    override suspend fun getAllCustomer(): List<CustomerEntity> = dbQuery {
        CustomerTable.selectAll().map(::resultCustomer)
    }

    override suspend fun addCustomerToOperation(
        customerId: Long,
        operationId: Long
    ): RelationshipCustomerToOperationEntity? = dbQuery {
        val insertStatement = RelationshipCustomerToOperationTable.insert { statement->
            statement[RelationshipCustomerToOperationTable.customerId] = customerId
            statement[RelationshipCustomerToOperationTable.operationId] = operationId
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToCustomerToOperation)
    }

    override suspend fun getCustomerToOperation(id: Long): RelationshipCustomerToOperationEntity? = dbQuery {
        RelationshipCustomerToOperationTable.select { RelationshipCustomerToOperationTable.id eq id }.singleOrNull()
            ?.let { resultToCustomerToOperation(it) }
    }

    override suspend fun getAllCustomerToOperation(): List<RelationshipCustomerToOperationEntity> = dbQuery {
        RelationshipCustomerToOperationTable.selectAll().map(::resultToCustomerToOperation)
    }

    override suspend fun getAllCustomerToOperationByCustomerId(customerId: Long): List<RelationshipCustomerToOperationEntity> = dbQuery {
        RelationshipCustomerToOperationTable.select { RelationshipCustomerToOperationTable.customerId eq customerId }
            .map(::resultToCustomerToOperation)
    }
    
    private fun resultCustomer(row: ResultRow) = CustomerEntity(
        id = row[CustomerTable.id],
        name = row[CustomerTable.name],
        phone = row[CustomerTable.phone],
        address = row[CustomerTable.address],
        scores = row[CustomerTable.scores]
    )

    private fun resultToCustomerToOperation(row : ResultRow) = RelationshipCustomerToOperationEntity(
        id = row[RelationshipCustomerToOperationTable.id] ,
        customerId = row[RelationshipCustomerToOperationTable.customerId],
        operationId = row[RelationshipCustomerToOperationTable.operationId]
    )

}