package com.flower.server.database_layer.database.impl

import com.flower.server.database.models.admin.UserTable
import com.flower.server.database.models.crm.CustomerTable
import com.flower.server.database.models.crm.FeedbackTable
import com.flower.server.database.models.crm.OrderTable
import com.flower.server.database.models.crm.RelationshipCustomerToOperationTable
import com.flower.server.database.models.product_info.*
import com.flower.server.database.models.storage.ProductCountTable
import com.flower.server.database.models.storage.StorageOperationTable
import com.flower.server.database.models.storage.StorageProductDataTable
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init(){
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(ProductsTable)
            SchemaUtils.create(GenreTable)
            SchemaUtils.create(TagTable)
            SchemaUtils.create(RelationshipGenreProductTable)
            SchemaUtils.create(RelationshipTagProductTable)

            SchemaUtils.create(ProductCountTable)
            SchemaUtils.create(StorageOperationTable)
            SchemaUtils.create(StorageProductDataTable)

            SchemaUtils.create(CustomerTable)
            SchemaUtils.create(RelationshipCustomerToOperationTable)
            SchemaUtils.create(OrderTable)
            SchemaUtils.create(FeedbackTable)

            SchemaUtils.create(UserTable)
        }
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }