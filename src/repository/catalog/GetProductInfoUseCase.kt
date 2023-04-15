package com.flower.server.repository.catalog

import com.flower.server.database.dao.*
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.catalog.GetProductResponse
import com.flower.server.web.models.response.catalog.ProductInfo
import java.rmi.ServerException

class GetProductInfoUseCase(private val productDao: ProductDao,
                            private val genreDao: GenreDao,
                            private val tagDao: TagDao,
                            private val relationshipProductToTagDao : RelationshipProductToTagDao,
                            private val relationshipProductToGenreDao : RelationshipProductToGenreDao
) : UseCase<IdRequest, GetProductResponse> {

    override suspend fun getResponse(request: IdRequest, token: String?): GetProductResponse{
        val product = productDao.getProduct(request.id) ?: throw ServerException("can't get product with id=${request.id}")
        val genres = getGenres(listOf(product.id), relationshipProductToGenreDao, genreDao)[product.id] ?: listOf()
        val tags = getTags(listOf(product.id), relationshipProductToTagDao, tagDao)[product.id] ?: listOf()
        return GetProductResponse(ProductInfo(
            product.id, product.name, product.description, product.photo, product.producer,
            product.gallery, genres, tags
        ))
    }

}