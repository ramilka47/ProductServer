package com.flower.server.database.dao.impl

import com.flower.server.database.dao.ICrmDao
import com.flower.server.database.models.crm.*
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CrmDaoImpl : ICrmDao {

    override suspend fun addCustomer(name: String, phone: String?, address: String?): CustomerEntity? = dbQuery {
        val insertStatement = CustomerTable.insert { statement->
            statement[CustomerTable.name] = name
            statement[CustomerTable.phone] = phone
            statement[CustomerTable.address] = address
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultCustomer)
    }

    override suspend fun addOrder(
        customerId: Long,
        operationId: Long,
        description: String,
        address: String,
        supplyStatus: SupplyStatus,
        createStatus: CreateStatus,
        deliveryStatus: DeliveryStatus,
        createDate : Long,
        updateDate : Long
    ): OrderEntity? = dbQuery {
        val insertStatement = OrderTable.insert { statement->
            statement[OrderTable.customerId] = customerId
            statement[OrderTable.operationId] = operationId
            statement[OrderTable.description] = description
            statement[OrderTable.address] = address
            statement[OrderTable.supplyStatus] = supplyStatus.name
            statement[OrderTable.createStatus] = createStatus.name
            statement[OrderTable.createDate] = createDate
            statement[OrderTable.deliveryStatus] = deliveryStatus.name
            statement[OrderTable.createDate] = createDate
            statement[OrderTable.updateDate] = updateDate
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToOrder)
    }

    override suspend fun addFeedback(
        operationId: Long?,
        productId: Long?,
        feedback: String,
        answer: String?,
        createDate : Long,
        updateDate : Long
    ): FeedbackEntity? = dbQuery {
        val insertStatement = FeedbackTable.insert { statement->
            statement[FeedbackTable.operationId] = operationId
            statement[FeedbackTable.productId] = productId
            statement[FeedbackTable.feedback] = feedback
            statement[FeedbackTable.answer] = answer
            statement[FeedbackTable.createDate] = createDate
            statement[FeedbackTable.updateDate] = updateDate
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultToFeedback)
    }

    override suspend fun updateCustomer(
        id: Long,
        name: String?,
        phone: String?,
        address: String?
    ): Boolean = dbQuery {
        CustomerTable.update({ CustomerTable.id eq id }){ statement->
            name?.let { statement[CustomerTable.name] = name }
            phone?.let { statement[CustomerTable.phone] = phone }
            address?.let { statement[CustomerTable.address] = address }
        } > 0
    }

    override suspend fun updateOrder(
        id: Long,
        description: String?,
        address: String?,
        supplyStatus: SupplyStatus?,
        createStatus: CreateStatus?,
        deliveryStatus: DeliveryStatus?,
        updateDate : Long
    ): Boolean = dbQuery {
        OrderTable.update({ OrderTable.id eq id }){ statement->
            description?.let { statement[OrderTable.description] = it }
            address?.let { statement[OrderTable.address] = it }
            supplyStatus?.let { statement[OrderTable.supplyStatus] = it.name }
            createStatus?.let { statement[OrderTable.createStatus] = it.name }
            deliveryStatus?.let { statement[OrderTable.deliveryStatus] = it.name }
            statement[OrderTable.updateDate] = updateDate
        } > 0
    }

    override suspend fun updateFeedback(
        id: Long,
        feedback: String?,
        answer: String?,
        updateDate: Long
    ): Boolean = dbQuery {
        FeedbackTable.update({ FeedbackTable.id eq id }){ statement->
            feedback?.let { statement[FeedbackTable.feedback] = it }
            answer?.let { statement[FeedbackTable.answer] = it }
            statement[FeedbackTable.updateDate] = updateDate
        } > 0
    }

    override suspend fun deleteFeedback(id: Long): Boolean = dbQuery {
        FeedbackTable.deleteWhere { FeedbackTable.id eq id } > 0
    }

    override suspend fun getCustomer(id: Long): CustomerEntity? = dbQuery { 
        CustomerTable.select { CustomerTable.id eq id }.singleOrNull()?.let { resultCustomer(it) }
    }

    override suspend fun getCustomer(name: String): CustomerEntity? = dbQuery {
        CustomerTable.select { CustomerTable.name eq name }.singleOrNull()?.let { resultCustomer(it) }
    }

    override suspend fun getOrder(id: Long): OrderEntity? = dbQuery {
        OrderTable.select { OrderTable.id eq id }.singleOrNull()?.let { resultToOrder(it) }
    }

    override suspend fun getFeedback(id: Long): FeedbackEntity? = dbQuery {
        FeedbackTable.select { FeedbackTable.id eq id }.singleOrNull()?.let { resultToFeedback(it) }
    }

    override suspend fun getAllCustomer(): List<CustomerEntity> = dbQuery {
        CustomerTable.selectAll().map(::resultCustomer)
    }

    override suspend fun getAllOrder(): List<OrderEntity> = dbQuery {
        OrderTable.selectAll().map(::resultToOrder)
    }

    override suspend fun getAllFeedbacks(): List<FeedbackEntity> = dbQuery {
        FeedbackTable.selectAll().map(::resultToFeedback)
    }

    override suspend fun getAllOrderByCustomerId(customerId: Long): List<OrderEntity> = dbQuery {
        OrderTable.select { OrderTable.customerId eq customerId }.map(::resultToOrder)
    }

    override suspend fun getAllFeedbacksByProductId(productId: Long): List<FeedbackEntity> = dbQuery {
        FeedbackTable.select { FeedbackTable.productId eq productId }.map(::resultToFeedback)
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

    override suspend fun getByOperationId(operationId: Long): RelationshipCustomerToOperationEntity? = dbQuery {
        RelationshipCustomerToOperationTable.select { RelationshipCustomerToOperationTable.operationId eq operationId }
            .singleOrNull()
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
        address = row[CustomerTable.address]
    )

    private fun resultToCustomerToOperation(row : ResultRow) = RelationshipCustomerToOperationEntity(
        id = row[RelationshipCustomerToOperationTable.id] ,
        customerId = row[RelationshipCustomerToOperationTable.customerId],
        operationId = row[RelationshipCustomerToOperationTable.operationId]
    )

    private fun resultToOrder(row: ResultRow) = OrderEntity(
        id = row[OrderTable.id],
        customerId = row[OrderTable.customerId],
        operationId = row[OrderTable.operationId],
        description = row[OrderTable.description],
        address = row[OrderTable.address],
        supplyStatus = SupplyStatus.valueOf(row[OrderTable.supplyStatus]),
        createStatus = CreateStatus.valueOf(row[OrderTable.createStatus]),
        deliveryStatus = DeliveryStatus.valueOf(row[OrderTable.deliveryStatus]),
        createDate = row[OrderTable.createDate],
        updateDate = row[OrderTable.updateDate]
    )

    private fun resultToFeedback(row: ResultRow) = FeedbackEntity(
        id = row[FeedbackTable.id],
        operationId = row[FeedbackTable.operationId],
        productId = row[FeedbackTable.productId],
        feedback = row[FeedbackTable.feedback],
        answer = row[FeedbackTable.answer],
        createDate = row[FeedbackTable.createDate],
        updateDate = row[FeedbackTable.updateDate]
    )

}