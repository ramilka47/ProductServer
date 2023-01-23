package com.flower.server.database.impl

import com.flower.server.database.models.*
import com.flower.server.database.modules.category.IDaoCategory
import com.flower.server.database.modules.categoryOfProducts.IDaoCategoryOfProducts
import com.flower.server.database.modules.cheque.IDaoCheque
import com.flower.server.database.modules.loss.IDaoLoss
import com.flower.server.database.modules.products.IDaoProducts
import com.flower.server.database.modules.receipt.IDaoReceipt
import com.flower.server.database.modules.selling.IDaoSelling
import com.flower.server.database.modules.user.IDaoUser
import com.flower.server.helper.Constants.PAGE_SIZE
import com.flower.server.helper.Constants.PAGE_SIZE_LONG
import com.flower.server.helper.gson
import com.flower.server.models.Sort
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DaoFacadeImpl :
    IDaoProducts,
    IDaoLoss,
    IDaoUser,
    IDaoReceipt,
    IDaoSelling,
    IDaoCategory,
    IDaoCategoryOfProducts,
    IDaoCheque {

    override suspend fun allProductOfCategoryId(categoryId: Long): List<CategoryOfProduct> = dbQuery {
        CategoryOfProducts.select{ CategoryOfProducts.categoryId eq categoryId}.map(::resultRowToCategoryOfProducts)
    }

    override suspend fun allProductOfCategoryIds(categoryIds: List<Long>): List<Category> = dbQuery{
        Categories.select{ Categories.id inList categoryIds}.map(::resultRowToCategory)
    }

    override suspend fun allProductOfCategoryProductIds(productIds: List<Long>): List<CategoryOfProduct> = dbQuery {
        CategoryOfProducts.select{ CategoryOfProducts.productId inList productIds}.map(::resultRowToCategoryOfProducts)
    }

    override suspend fun allSellingByIds(ids: List<Long>): List<Selling> = dbQuery {
        Sellings.select { Sellings.id inList ids }.map(::resultRowToSelling)
    }

    override suspend fun productByIds(ids: List<Long>): List<Product> = dbQuery{
        Products.select { Products.id inList ids }.map(::resultRowToProducts)
    }

    override suspend fun getUserById(id: Long): User? = dbQuery {
        Users
            .select{ Users.id eq id }
            .map(this::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun getUserByLogin(login: String): User? = dbQuery {
        Users
            .select{ Users.login eq login }
            .map(this::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun getUserByToken(token: String): User? = dbQuery {
        Users
            .select{ Users.token eq token }
            .map(this::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun deleteUser(id: Long): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id  } > 0
    }

    override suspend fun addUser(
        name: String,
        login: String,
        password: String,
        token: String,
        date: Long,
        level : Int): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.name] = name
            it[Users.login] = login
            it[Users.password] = password
            it[Users.token] = token
            it[Users.date] = date
            it[Users.level] = level
        }

        insertStatement.resultedValues?.singleOrNull()?.let(this::resultRowToUser)
    }

    override suspend fun updateUser(
        id: Long,
        name: String,
        login: String,
        password: String,
        token: String,
        date: Long,
        level : Int
    ): Boolean = dbQuery {
        Users.update({ Users.id eq id }){
            it[Users.name] = name
            it[Users.login] = login
            it[Users.password] = password
            it[Users.token] = token
            it[Users.date] = date
            it[Users.level] = level
        } > 0
    }

    override suspend fun productByCode(code: String): Product? = dbQuery {
        Products
            .select { Products.code eq code }
            .map(::resultRowToProducts)
            .singleOrNull()
    }

    override suspend fun product(id: Long): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProducts)
            .singleOrNull()
    }

    override suspend fun loss(id: Long): Loss? = dbQuery {
        Losses
            .select { Losses.id eq id }
            .map(::resultRowToLoss)
            .singleOrNull()
    }

    override suspend fun receipt(id: Long): Receipt? = dbQuery {
        Receipts
            .select { Receipts.id eq id }
            .map(::resultRowToReceipt)
            .singleOrNull()
    }

    override suspend fun selling(id: Long): Selling? = dbQuery {
        Sellings
            .select { Sellings.id eq id }
            .map(::resultRowToSelling)
            .singleOrNull()
    }

    override suspend fun category(id: Long): Category? = dbQuery {
        Categories
            .select { Categories.id eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    override suspend fun cheque(id: Long): Cheque? = dbQuery {
        Cheques
            .select { Cheques.id eq id }
            .map(::resultRowToCheque)
            .singleOrNull()
    }

    override suspend fun categoryOfProducts(id: Long): CategoryOfProduct? = dbQuery {
        CategoryOfProducts
            .select { CategoryOfProducts.productId eq id }
            .map(::resultRowToCategoryOfProducts)
            .singleOrNull()
    }

    override suspend fun allProductsByName(name: String): List<Product> = dbQuery {
        Products
            .select { Products.name regexp name }
            .map(::resultRowToProducts)
    }

    override suspend fun allProducts(): List<Product> = dbQuery{
        Products.selectAll().map(::resultRowToProducts)
    }

    override suspend fun allLoss(): List<Loss> = dbQuery {
        Losses.selectAll().map(::resultRowToLoss)
    }

    override suspend fun allReceipt(): List<Receipt> = dbQuery {
        Receipts.selectAll().map(::resultRowToReceipt)
    }

    override suspend fun allSelling(): List<Selling> = dbQuery {
        Sellings.selectAll().map(::resultRowToSelling)
    }

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun allCategory(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    override suspend fun allCheques(): List<Cheque> = dbQuery {
        Cheques.selectAll().map(::resultRowToCheque)
    }

    override suspend fun allCategoryOfProducts(): List<CategoryOfProduct> = dbQuery {
        CategoryOfProducts.selectAll().map(::resultRowToCategoryOfProducts)
    }

    override suspend fun productsByPageSort(sort: Sort, offset: Int): List<Product> = dbQuery {
        Products.selectAll().orderBy(
            when(sort){
                Sort.DATE ->
                    Products.id
                Sort.NAME ->
                    Products.name
                Sort.PRICE ->
                    Products.price
                Sort.DEFAULT ->
                    Products.id
            }
        ).limit(PAGE_SIZE, offset * PAGE_SIZE_LONG).map(::resultRowToProducts)
    }

    override suspend fun productsSize() : Long = dbQuery {
        Products.selectAll().count()
    }

    override suspend fun lossesSize(): Long = dbQuery {
        Losses.selectAll().count()
    }

    override suspend fun receiptsSize(): Long = dbQuery {
        Receipts.selectAll().count()
    }

    override suspend fun sellingsSize(): Long = dbQuery {
        Sellings.selectAll().count()
    }

    override suspend fun usersSize(): Long = dbQuery {
        Users.selectAll().count()
    }

    override suspend fun lossesByPageSort(sort: Sort, offset: Int): List<Loss> = dbQuery {
        Losses.selectAll().orderBy(
            when(sort){
                Sort.DATE ->
                    Losses.date
                Sort.NAME ->
                    Losses.name
                Sort.PRICE ->
                    Losses.salePrice
                Sort.DEFAULT ->
                    Losses.id
            }
        ).limit(PAGE_SIZE, offset * PAGE_SIZE_LONG).map(::resultRowToLoss)
    }

    override suspend fun receiptsByPageSort(sort: Sort, offset: Int): List<Receipt> = dbQuery {
        Receipts.selectAll().orderBy(
            when(sort){
                Sort.DATE ->
                    Receipts.date
                Sort.NAME ->
                    Receipts.name
                Sort.PRICE ->
                    Receipts.salePrice
                Sort.DEFAULT ->
                    Receipts.id
            }
        ).limit(PAGE_SIZE, offset * PAGE_SIZE_LONG).map(::resultRowToReceipt)
    }

    override suspend fun sellingsByPageSort(sort: Sort, offset: Int): List<Selling> = dbQuery {
        Sellings.selectAll().orderBy(
            when(sort){
                Sort.DATE ->
                    Sellings.date
                Sort.NAME ->
                    Sellings.name
                Sort.PRICE ->
                    Sellings.salePrice
                Sort.DEFAULT ->
                    Sellings.id
            }
        ).limit(PAGE_SIZE, offset * PAGE_SIZE_LONG).map(::resultRowToSelling)
    }

    override suspend fun addCheque(
        price: Double,
        name: String,
        sellingIds: List<Long>,
        date: Long): Cheque? = dbQuery {
        val insertStatement = Cheques.insert {
            it[Cheques.price] = price
            it[Cheques.name] = name
            it[Cheques.sellings] = gson.toJson(sellings)
            it[Cheques.date] = date
            it[Cheques.canceled] = false
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCheque)
    }

    override suspend fun addProduct(
        name: String,
        description: String,
        photo: String,
        count: Int,
        price: Double,
        code : String
    ): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.description] = description
            it[Products.photo] = photo
            it[Products.count] = count
            it[Products.price] = price
            it[Products.code] = code
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProducts)
    }

    override suspend fun addLoss(
        productId: Long,
        description: String,
        date: Long,
        count: Int,
        name: String,
        salePrice : Double
    ): Loss? = dbQuery {
        val insertStatement = Losses.insert {
            it[Losses.product] = productId.toString()
            it[Losses.description] = description
            it[Losses.date] = date
            it[Losses.count] = count
            it[Losses.name] = name
            it[Losses.salePrice] = salePrice
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToLoss)
    }

    override suspend fun addReceipt(
        productId: Long,
        description: String,
        date: Long,
        count: Int,
        name: String,
        salePrice : Double
    ): Receipt? = dbQuery {
        val insertStatement = Receipts.insert {
            it[Receipts.product] = productId.toString()
            it[Receipts.description] = description
            it[Receipts.date] = date
            it[Receipts.count] = count
            it[Receipts.name] = name
            it[Receipts.salePrice] = salePrice
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToReceipt)
    }

    override suspend fun addSelling(
        productId: Long,
        description: String,
        date: Long,
        count: Int,
        name: String,
        salePrice : Double
    ): Selling? = dbQuery {
        val insertStatement = Sellings.insert {
            it[Sellings.product] = productId.toString()
            it[Sellings.description] = description
            it[Sellings.date] = date
            it[Sellings.count] = count
            it[Sellings.name] = name
            it[Sellings.salePrice] = salePrice
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToSelling)
    }

    override suspend fun addCategory(name: String): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[Categories.name] = name
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun addCategoryOfProducts(productId: Long, categoryId: Long): CategoryOfProduct? =
        dbQuery {
            val insertStatement = CategoryOfProducts.insert {
                it[CategoryOfProducts.productId] = productId
                it[CategoryOfProducts.categoryId] = categoryId
            }

            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategoryOfProducts)
        }

    override suspend fun updateCountProduct(id: Long, count: Int) = dbQuery {
        Products.update({ Products.id eq id }){
            it[Products.count] = count
        } > 0
    }

    override suspend fun updateChequeCanceled(id: Long,
                                              canceled: Boolean) = dbQuery {
        Cheques.update({ Cheques.id eq id }){
            it[Cheques.canceled] = canceled
        } > 0
    }

    override suspend fun updateProduct(
        id: Long,
        name: String,
        description: String,
        photo: String,
        count: Int,
        price: Double,
        code : String
    ): Boolean = dbQuery {
        Products.update({ Products.id eq id }){
            it[Products.name] = name
            it[Products.description] = description
            it[Products.photo] = photo
            it[Products.count] = count
            it[Products.price] = price
            it[Products.code] = code
        } > 0
    }

    override suspend fun updateLoss(
        id: Long,
        productId: Long,
        description: String,
        date: Long,
        count: Int,
        name: String,
        salePrice : Double
    ): Boolean = dbQuery {
        Losses.update({ Losses.id eq id }){
            it[Losses.product] = productId.toString()
            it[Losses.description] = description
            it[Losses.date] = date
            it[Losses.count] = count
            it[Losses.name] = name
            it[Losses.salePrice] = salePrice
        } > 0
    }

    override suspend fun updateReceipt(
        id: Long,
        productId: Long,
        description: String,
        date: Long,
        count: Int,
        name: String,
        salePrice : Double
    ): Boolean = dbQuery {
        Receipts.update({ Receipts.id eq id }){
            it[Receipts.product] = productId.toString()
            it[Receipts.description] = description
            it[Receipts.date] = date
            it[Receipts.count] = count
            it[Receipts.name] = name
            it[Receipts.salePrice] = salePrice
        } > 0
    }

    override suspend fun updateSelling(
        id: Long,
        productId: Long,
        description: String,
        date: Long,
        count: Int,
        name: String,
        salePrice : Double
    ): Boolean = dbQuery {
        Sellings.update({ Sellings.id eq id }){
            it[Sellings.product] = productId.toString()
            it[Sellings.description] = description
            it[Sellings.date] = date
            it[Sellings.count] = count
            it[Sellings.name] = name
            it[Sellings.salePrice] = salePrice
        } > 0
    }

    override suspend fun updateCategory(id: Long, name: String): Boolean = dbQuery {
        Categories.update({ Categories.id eq id }){
            it[Categories.name] = name
        } > 0
    }

    override suspend fun updateCategoryOfProducts(productId: Long, categoryId: Long): Boolean = dbQuery {
        CategoryOfProducts.update({ CategoryOfProducts.productId eq productId }){
            it[CategoryOfProducts.categoryId] = categoryId
        } > 0
    }

    override suspend fun deleteProduct(id: Long): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id  } > 0
    }

    override suspend fun deleteLoss(id: Long): Boolean = dbQuery {
        Losses.deleteWhere { Losses.id eq id  } > 0
    }

    override suspend fun deleteReceipt(id: Long): Boolean = dbQuery {
        Receipts.deleteWhere { Receipts.id eq id  } > 0
    }

    override suspend fun deleteSelling(id: Long): Boolean = dbQuery {
        Sellings.deleteWhere { Sellings.id eq id  } > 0
    }

    override suspend fun deleteCategory(id: Long): Boolean = dbQuery {
        Categories.deleteWhere { Categories.id eq id  } > 0
    }

    override suspend fun deleteCategoryOfProducts(id: Long): Boolean = dbQuery {
        CategoryOfProducts.deleteWhere { CategoryOfProducts.productId eq id  } > 0
    }

    private fun resultRowToCategoryOfProducts(row: ResultRow) = CategoryOfProduct(
        productId = row[CategoryOfProducts.productId],
        categoryId = row[CategoryOfProducts.categoryId]
    )

    private fun resultRowToCategory(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name]
    )

    private fun resultRowToProducts(row : ResultRow) = Product(
        id = row[Products.id].toString(),
        name = row[Products.name],
        description = row[Products.description],
        photo = row[Products.photo],
        count = row[Products.count],
        price = row[Products.price],
        code = row[Products.code]
    )

    private fun resultRowToLoss(row : ResultRow) = Loss(
        id = row[Losses.id].toString(),
        name = row[Losses.name],
        description = row[Losses.description],
        date = row[Losses.date],
        count = row[Losses.count],
        product = runBlocking { product(row[Losses.product].toLong())!! },
        salePrice = row[Losses.salePrice]
    )

    private fun resultRowToReceipt(row : ResultRow) = Receipt(
        id = row[Receipts.id].toString(),
        name = row[Receipts.name],
        description = row[Receipts.description],
        date = row[Receipts.date],
        count = row[Receipts.count],
        product = runBlocking { product(row[Receipts.product].toLong())!! },
        salePrice = row[Receipts.salePrice]
    )

    private fun resultRowToSelling(row : ResultRow) = Selling(
        id = row[Sellings.id].toString(),
        name = row[Sellings.name],
        description = row[Sellings.description],
        date = row[Sellings.date],
        count = row[Sellings.count],
        product = runBlocking { product(row[Sellings.product].toLong())!! },
        salePrice = row[Sellings.salePrice]
    )

    private fun resultRowToUser(row : ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        login = row[Users.login],
        password = row[Users.password],
        token = row[Users.token],
        date = row[Users.date],
        level = row[Users.level]
    )

    private fun resultRowToCheque(row: ResultRow) = Cheque(
        id = row[Cheques.id],
        price = row[Cheques.price],
        name = row[Cheques.name],
        sellings = row[Cheques.sellings],
        date = row[Cheques.date],
        canceled = row[Cheques.canceled]
    )

/*    private suspend fun lossFromRow(row : ResultRow) : Loss{
        val id = row[Losses.id].toString()
        val name = row[Losses.name]
        val description = row[Losses.description]
        val date = row[Losses.date]
        val count = row[Losses.count]
        val product = Products.select(Products.id eq row[Losses.product].toLong()).map(this::resultRowToProducts).singleOrNull()
        return Loss(id, product!!, description, date, count, name)
    }

    private suspend fun receiptFromRow(row : ResultRow) : Receipt{
        val id = row[Receipts.id].toString()
        val name = row[Receipts.name]
        val description = row[Receipts.description]
        val date = row[Receipts.date]
        val count = row[Receipts.count]
        val product = Products.select(Products.id eq row[Receipts.product].toLong()).map(this::resultRowToProducts).singleOrNull()
        return Receipt(id, product!!, description, date, count, name)
    }
    private suspend fun sellingFromRow(row : ResultRow) : Selling{
        val id = row[Losses.id].toString()
        val name = row[Losses.name]
        val description = row[Losses.description]
        val date = row[Losses.date]
        val count = row[Losses.count]
        val product = Products.select(Products.id eq row[Losses.product].toLong()).map(this::resultRowToProducts).singleOrNull()
        return Selling(id, product!!, description, date, count, name)
    }*/
}