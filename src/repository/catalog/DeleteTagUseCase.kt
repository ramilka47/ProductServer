package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.TagDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.DeleteTagRequest
import com.flower.server.web.models.response.catalog.DeleteTagResponse
import java.rmi.ServerException

class DeleteTagUseCase(private val tagDao : TagDao,
                       private val userDao: UserDao,
                       private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<DeleteTagRequest, DeleteTagResponse> {

    override suspend fun getResponse(request: DeleteTagRequest, token: String?): DeleteTagResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level){
            if (tagDao.deleteTag(request.id)){
                throw ServerException("can't delete tag")
            }
            DeleteTagResponse(true)
        }
    }
}