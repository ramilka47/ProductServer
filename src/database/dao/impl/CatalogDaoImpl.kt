package com.flower.server.database.dao.impl

import com.flower.server.database.dao.IDaoCatalog
import com.flower.server.database.models.product_info.*
import com.flower.server.database_layer.database.impl.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

class CatalogDaoImpl : IDaoCatalog {

    override suspend fun addProduct(name: String, description: String?, photo: String?, producer: String?): ProductEntity? = dbQuery {
        val insertStatement = ProductsTable.insert { statement->
            statement[ProductsTable.name] = name
            statement[ProductsTable.description] = description
            statement[ProductsTable.photo] = photo
            statement[ProductsTable.producer] = producer
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProducts)
    }

    override suspend fun addGenre(name: String): GenreEntity? = dbQuery {
        val insertStatement = GenreTable.insert { statement->
            statement[GenreTable.name] = name
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToGenres)
    }

    override suspend fun addTag(name: String): TagEntity? = dbQuery {
        val insertStatement = TagTable.insert { statement->
            statement[TagTable.name] = name
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTags)
    }

    override suspend fun addGenreInProduct(genreId: Long, productId: Long): Boolean = dbQuery {
        val insertStatement = RelationshipGenreProductTable.insert { statement->
            statement[RelationshipGenreProductTable.productId] = productId
            statement[RelationshipGenreProductTable.genreId] = genreId
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRelationshipGenreToProducts) != null
    }

    override suspend fun addProductInGenre(productId: Long, genreId: Long): Boolean = dbQuery {
        val insertStatement = RelationshipGenreProductTable.insert { statement->
            statement[RelationshipGenreProductTable.productId] = productId
            statement[RelationshipGenreProductTable.genreId] = genreId
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRelationshipGenreToProducts) != null
    }

    override suspend fun addProductInTag(productId: Long, tagId: Long): Boolean = dbQuery {
        val insertStatement = RelationshipTagProductTable.insert { statement->
            statement[RelationshipTagProductTable.productId] = productId
            statement[RelationshipTagProductTable.tagId] = tagId
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRelationshipTagToProducts) != null
    }

    override suspend fun addTagInProduct(tagId: Long, productId: Long): Boolean = dbQuery {
        val insertStatement = RelationshipTagProductTable.insert { statement->
            statement[RelationshipTagProductTable.productId] = productId
            statement[RelationshipTagProductTable.tagId] = tagId
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRelationshipTagToProducts) != null
    }

    override suspend fun deleteProduct(product: ProductEntity): Boolean = dbQuery {
        ProductsTable.deleteWhere { id eq product.id  } > 0
    }

    override suspend fun deleteProduct(id: Long): Boolean  = dbQuery {
        ProductsTable.deleteWhere { ProductsTable.id eq id  } > 0
    }

    override suspend fun deleteAllProduct(): Boolean = dbQuery {
        ProductsTable.deleteAll() > 0
    }

    override suspend fun deleteAllProduct(id: List<Long>): Boolean = dbQuery {
        ProductsTable.deleteWhere { ProductsTable.id inList id } > 0
    }

    override suspend fun deleteGenre(id: Long): Boolean = dbQuery {
        GenreTable.deleteWhere { GenreTable.id eq id } > 0
    }

    override suspend fun deleteTag(id: Long) : Boolean = dbQuery {
        TagTable.deleteWhere { TagTable.id eq id } > 0
    }

    override suspend fun deleteRelationshipGenreToProduct(id: Long): Boolean = dbQuery {
        RelationshipGenreProductTable.deleteWhere { RelationshipGenreProductTable.id eq id } > 0
    }

    override suspend fun deleteRelationshipTagToProduct(id: Long): Boolean = dbQuery {
        RelationshipTagProductTable.deleteWhere { RelationshipTagProductTable.id eq id } > 0
    }

    override suspend fun updateProduct(
        id: Long,
        name: String,
        description: String?,
        photo: String?,
        producer: String?
    ): Boolean = dbQuery {
        ProductsTable.update({ ProductsTable.id eq id }){ statement->
            statement[ProductsTable.name] = name
            statement[ProductsTable.description] = description
            statement[ProductsTable.photo] = photo
            statement[ProductsTable.producer] = producer
        } > 0
    }

    override suspend fun updateGenre(id: Long, name: String): Boolean = dbQuery {
        GenreTable.update({ GenreTable.id eq id }){ statement->
            statement[GenreTable.name] = name
        } > 0
    }

    override suspend fun updateTag(id: Long, name: String): Boolean = dbQuery {
        TagTable.update({ TagTable.id eq id }){ statement->
            statement[TagTable.name] = name
        } > 0
    }

    override suspend fun getProduct(id: Long): ProductEntity? = dbQuery {
        ProductsTable
            .select { ProductsTable.id eq id }
            .singleOrNull()
            ?.let { resultRowToProducts(it) }
    }

    override suspend fun getProduct(name: String): ProductEntity? = dbQuery {
        ProductsTable
            .select { ProductsTable.name regexp name }
            .singleOrNull()?.let { resultRowToProducts(it) }
    }

    override suspend fun getAllProduct(): List<ProductEntity> = dbQuery {
        ProductsTable
            .selectAll()
            .map(::resultRowToProducts)
    }

    override suspend fun getAllGenres(): List<GenreEntity> = dbQuery {
        GenreTable
            .selectAll()
            .map(::resultRowToGenres)
    }

    override suspend fun getAllTags(): List<TagEntity> = dbQuery {
        TagTable
            .selectAll()
            .map(::resultRowToTags)
    }

    override suspend fun getAllGenresByIds(ids: List<Long>): List<GenreEntity> = dbQuery {
        GenreTable.select{ GenreTable.id inList ids }.map(::resultRowToGenres)
    }

    override suspend fun getAllTagsById(ids: List<Long>): List<TagEntity> = dbQuery {
        TagTable.select{ TagTable.id inList ids }.map(::resultRowToTags)
    }

    override suspend fun getAllRelationshipByGenre(genreId: Long): List<RelationshipGenreProductEntity> = dbQuery {
        RelationshipGenreProductTable.select{ RelationshipGenreProductTable.genreId eq genreId }.map(::resultRelationshipGenreToProducts)
    }

    override suspend fun getAllRelationshipByTag(tagId: Long): List<RelationshipTagProductEntity> = dbQuery {
        RelationshipTagProductTable.select{ RelationshipTagProductTable.tagId eq tagId }.map(::resultRelationshipTagToProducts)
    }

    override suspend fun getAllRelationshipGenreByProduct(productId: Long): List<RelationshipGenreProductEntity> = dbQuery {
        RelationshipGenreProductTable.select{ RelationshipGenreProductTable.productId eq productId }.map(::resultRelationshipGenreToProducts)
    }

    override suspend fun getAllRelationshipTagByProduct(productId: Long): List<RelationshipTagProductEntity> = dbQuery {
        RelationshipTagProductTable.select{ RelationshipTagProductTable.productId eq productId }.map(::resultRelationshipTagToProducts)
    }

    private fun resultRowToProducts(row : ResultRow) = ProductEntity(
        id = row[ProductsTable.id],
        name = row[ProductsTable.name],
        description = row[ProductsTable.description],
        photo = row[ProductsTable.photo],
        producer = row[ProductsTable.producer]
    )

    private fun resultRowToTags(row: ResultRow) = TagEntity(
        id = row[TagTable.id],
        name = row[TagTable.name]
    )

    private fun resultRowToGenres(row: ResultRow) = GenreEntity(
        id = row[GenreTable.id],
        name = row[GenreTable.name]
    )

    private fun resultRelationshipTagToProducts(row: ResultRow) = RelationshipTagProductEntity(
        id = row[RelationshipTagProductTable.id],
        productId = row[RelationshipTagProductTable.productId],
        tagId = row[RelationshipTagProductTable.tagId]
    )

    private fun resultRelationshipGenreToProducts(row: ResultRow) = RelationshipGenreProductEntity(
        id = row[RelationshipGenreProductTable.id],
        productId = row[RelationshipGenreProductTable.productId],
        genreId = row[RelationshipGenreProductTable.genreId]
    )

}