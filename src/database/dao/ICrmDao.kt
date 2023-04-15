package com.flower.server.database.dao

import com.flower.server.database.models.crm.*
import com.flower.server.database.models.crm.FeedbackEntity

interface ICrmDao : CustomerDao, RelationshipCustomerToOperationDao, OrderDao, FeedbackDao

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

interface OrderDao{

    suspend fun addOrder(customerId : Long,
                         operationId : Long,
                         description : String,
                         address : String,
                         supplyStatus : SupplyStatus,
                         createStatus : CreateStatus,
                         deliveryStatus : DeliveryStatus,
                         createDate : Long,
                         updateDate : Long) : OrderEntity?

    suspend fun updateOrder(id : Long,
                            description : String? = null,
                            address : String? = null,
                            supplyStatus : SupplyStatus? = null,
                            createStatus : CreateStatus? = null,
                            deliveryStatus : DeliveryStatus? = null,
                            updateDate : Long) : Boolean

    suspend fun getOrder(id : Long) : OrderEntity?

    suspend fun getAllOrderByCustomerId(customerId: Long) : List<OrderEntity>

    suspend fun getAllOrder() : List<OrderEntity>
}

interface FeedbackDao{

    suspend fun addFeedback(operationId : Long? = null,
                            productId : Long? = null,
                            feedback : String,
                            answer : String? = null,
                            createDate : Long,
                            updateDate : Long) : FeedbackEntity?

    suspend fun updateFeedback(id : Long,
                               feedback : String? = null,
                               answer : String? = null,
                               updateDate : Long) : Boolean

    suspend fun deleteFeedback(id : Long) : Boolean

    suspend fun getFeedback(id : Long) : FeedbackEntity?

    suspend fun getAllFeedbacks() : List<FeedbackEntity>

    suspend fun getAllFeedbacksByProductId(productId: Long) : List<FeedbackEntity>
}

