package com.flower.server.database

import com.flower.server.database.models.admin.UserTable
import com.flower.server.database.models.crm.CustomerTable
import com.flower.server.database.models.crm.RelationshipCustomerToOperationTable
import com.flower.server.database.models.product_info.*
import com.flower.server.database.models.storage.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.xml.crypto.Data

object TestDatabaseFactory {
    fun init(){
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/dbtest"
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

            SchemaUtils.create(UserTable)
        }
    }
}
