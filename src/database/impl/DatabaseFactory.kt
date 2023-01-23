package com.flower.server.database.impl

import com.flower.server.database.models.*
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
            SchemaUtils.create(Products)
            SchemaUtils.create(Losses)
            SchemaUtils.create(Receipts)
            SchemaUtils.create(Sellings)
            SchemaUtils.create(Users)
            SchemaUtils.create(Categories)
            SchemaUtils.create(CategoryOfProducts)
            SchemaUtils.create(Cheques)
        }
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }