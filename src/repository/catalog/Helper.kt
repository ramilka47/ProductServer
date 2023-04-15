package com.flower.server.repository.catalog

import com.flower.server.database.dao.GenreDao
import com.flower.server.database.dao.RelationshipProductToGenreDao
import com.flower.server.database.dao.RelationshipProductToTagDao
import com.flower.server.database.dao.TagDao
import com.flower.server.web.models.response.catalog.Genre
import com.flower.server.web.models.response.catalog.Tag
import com.flower.server.web.models.response.catalog.toGenre
import com.flower.server.web.models.response.catalog.toTag

internal suspend fun getGenres(productIds : List<Long>, relationshipProductToGenreDao: RelationshipProductToGenreDao, genreDao: GenreDao) : Map<Long, List<Genre>>{
    val map = hashMapOf<Long, List<Genre>>()
    productIds.forEach { id ->
        map[id] = genreDao
            .getAllGenresByIds(
                relationshipProductToGenreDao
                    .getAllRelationshipGenreByProduct(id)
                    .map { it.genreId })
            .map { genre-> genre.toGenre() }
    }
    return map
}

internal suspend fun getTags(productIds : List<Long>, relationshipProductToTagDao: RelationshipProductToTagDao, tagDao: TagDao) : Map<Long, List<Tag>> {
    val map = hashMapOf<Long, List<Tag>>()
    productIds.forEach { id ->
        map[id] = tagDao.getAllTagsById(
            relationshipProductToTagDao.getAllRelationshipTagByProduct(id).map { it.productId }
        ).map { tag-> tag.toTag() }
    }
    return map
}

