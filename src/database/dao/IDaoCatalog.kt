package com.flower.server.database.dao

import com.flower.server.database.models.product_info.*

interface IDaoCatalog : ProductDao, TagDao, GenreDao, RelationshipProductToTagDao, RelationshipProductToGenreDao

interface ProductDao{
    suspend fun addProduct(name : String,
                           description : String? = null,
                           photo : String? = null,
                           producer : String? = null) : ProductEntity?

    suspend fun deleteProduct(product: ProductEntity) : Boolean

    suspend fun deleteProduct(id : Long) : Boolean

    suspend fun deleteAllProduct() : Boolean

    suspend fun deleteAllProduct(id : List<Long>) : Boolean

    suspend fun updateProduct(id: Long,
                              name : String,
                              description : String? = null,
                              photo : String? = null,
                              producer : String? = null) : Boolean

    suspend fun getProduct(id : Long) : ProductEntity?

    suspend fun getProduct(name : String) : ProductEntity?

    suspend fun getAllProduct() : List<ProductEntity>
}

interface TagDao{
    suspend fun addTag(name: String) : TagEntity?

    suspend fun updateTag(id: Long, name: String) : Boolean

    suspend fun deleteTag(id: Long) : Boolean

    suspend fun getAllTags() : List<TagEntity>

    suspend fun getAllTagsById(ids : List<Long>) : List<TagEntity>
}

interface GenreDao{
    suspend fun addGenre(name: String) : GenreEntity?

    suspend fun updateGenre(id: Long, name: String) : Boolean

    suspend fun deleteGenre(id: Long) : Boolean

    suspend fun getAllGenres() : List<GenreEntity>

    suspend fun getAllGenresByIds(ids : List<Long>) : List<GenreEntity>
}

interface RelationshipProductToTagDao{

    suspend fun addProductInTag(productId : Long, tagId : Long) : Boolean

    suspend fun addTagInProduct(tagId: Long, productId: Long) : Boolean

    suspend fun deleteRelationshipTagToProduct(id : Long) : Boolean

    suspend fun getAllRelationshipByTag(tagId: Long) : List<RelationshipTagProductEntity>

    suspend fun getAllRelationshipTagByProduct(productId: Long) : List<RelationshipTagProductEntity>
}

interface RelationshipProductToGenreDao{

    suspend fun addProductInGenre(productId : Long, genreId : Long) : Boolean

    suspend fun addGenreInProduct(genreId: Long, productId: Long) : Boolean

    suspend fun deleteRelationshipGenreToProduct(id : Long) : Boolean

    suspend fun getAllRelationshipByGenre(genreId: Long) : List<RelationshipGenreProductEntity>

    suspend fun getAllRelationshipGenreByProduct(productId: Long) : List<RelationshipGenreProductEntity>
}