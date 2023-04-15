package com.flower.server.database

import com.flower.server.database.models.admin.UserTable
import com.flower.server.database.models.bonus_club.BonusCardTable
import com.flower.server.database.models.bonus_club.BonusCountTable
import com.flower.server.database.models.crm.CustomerTable
import com.flower.server.database.models.crm.FeedbackTable
import com.flower.server.database.models.crm.OrderTable
import com.flower.server.database.models.crm.RelationshipCustomerToOperationTable
import com.flower.server.database.models.product_info.*
import com.flower.server.database.models.provider.ProviderTable
import com.flower.server.database.models.provider.ProviderToProductTable
import com.flower.server.database.models.storage.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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
            SchemaUtils.create(OrderTable)
            SchemaUtils.create(FeedbackTable)

            SchemaUtils.create(UserTable)

            SchemaUtils.create(ProviderTable)
            SchemaUtils.create(ProviderToProductTable)

            SchemaUtils.create(BonusCountTable)
            SchemaUtils.create(BonusCardTable)
        }
    }
}
