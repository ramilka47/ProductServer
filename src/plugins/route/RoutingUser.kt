package com.flower.server.plugins.route

import com.flower.server.database.models.CategoryOfProduct
import com.flower.server.database.models.Product
import com.flower.server.helper.*
import com.flower.server.helper.EndPoints.GET_ALL_CATEGORY_ENDPOINT
import com.flower.server.helper.EndPoints.GET_ALL_PRODUCT_BY_CATEGORY_ENDPOINT
import com.flower.server.helper.EndPoints.GET_ALL_PRODUCT_ENDPOINT
import com.flower.server.helper.EndPoints.GET_PHOTO_ENDPOINT
import com.flower.server.helper.EndPoints.GET_PRODUCT_BY_CODE_ENDPOINT
import com.flower.server.helper.EndPoints.GET_PRODUCT_BY_ID_ENDPOINT
import com.flower.server.helper.EndPoints.GET_SEARCH_RESULT_PRODUCT_BY_NAME_ENDPOINT
import com.flower.server.helper.EndPoints.POST_AUTH_LOGIN_ENDPOINT
import com.flower.server.helper.EndPoints.POST_PAGE_WITH_PRODUCT_OFFSET_ENDPOINT
import com.flower.server.helper.execeptions.*
import com.flower.server.models.Sort
import com.flower.server.models.request.LoginUser
import com.flower.server.models.request.PageRequest
import com.flower.server.models.request.SearchQueryRequest
import com.flower.server.models.result.*
import com.flower.server.plugins.interactors.*
import com.google.gson.JsonParseException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*
import java.io.File

@KtorDsl
fun Route.RoutingUser() {

    /*логин пользователя - отдача токена*/
    post(POST_AUTH_LOGIN_ENDPOINT) {
        call.withUserAgent {
            respondOk(
                PostLoginUserInteractor
                    .getResponse(
                        gson
                            .fromJson(
                                receiveText(),
                                LoginUser::class.java
                            )
                    )
            )
        }
    }

    /*Получение товаров. поиск по имени*/
    get(GET_SEARCH_RESULT_PRODUCT_BY_NAME_ENDPOINT) {
        call.withUserAgent {
            val searchQuery = request.queryParameters["q"]
            if (searchQuery == null || searchQuery.isBlank()) {
                respond(HttpStatusCode(500, "error"), "searchQuery is empty")
            } else {
                respondOk(GetSearchResultByQueryInteractor.getResponse(SearchQueryRequest(searchQuery)))
            }
        }
    }

    /*Получение товаров.*/
    get(GET_ALL_PRODUCT_ENDPOINT) {
        call.withUserAgent {
            respondOk(GetAllProductInteractor.getResponseWithEmpty())
        }
    }

    /*Получение товара.*/
    get(GET_PRODUCT_BY_ID_ENDPOINT) {
        call.withUserAgent {
            val id = parameters.getOrFail("id")
            val result: Product? = daoProductsGetter.product(id.toLong())
            respond(HttpStatusCode(200, "ok"), gson.toJson(result))
        }
    }

    get(GET_PRODUCT_BY_CODE_ENDPOINT) {
        call.withUserAgent {
            respondOk(GetProductByCodeInteractor.getResponseWithId(parameters.getOrFail("code")))
        }
    }

    /*Получение товаров paging*/
    post(POST_PAGE_WITH_PRODUCT_OFFSET_ENDPOINT) {
        call.withUserAgent {
            respondOk(
                PostProductPageInteractor.getResponse(
                    try {
                        gson.fromJson(receiveText(), PageRequest::class.java)
                    } catch (e: JsonParseException) {
                        PageRequest(1, Sort.DEFAULT)
                    }
                )
            )
        }
    }

    /*Получение картинки*/
    get(GET_PHOTO_ENDPOINT) {
        call.withUserAgent {
            val id = parameters.getOrFail("id")
            response.header(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
            val file = File(photo(id))
            respond(HttpStatusCode(200, "ok"), file.readBytes())
        }
    }

    /*Получение категорий*/
    get(GET_ALL_CATEGORY_ENDPOINT) {
        call.withUserAgent {
            respondOk(GetAllCategoryInteractor.getResponseWithEmpty())
        }
    }

    /*Получение товарой данной категории*/
    get(GET_ALL_PRODUCT_BY_CATEGORY_ENDPOINT) {
        call.withUserAgent {
            respondOk(GetAllProductByCategoryInteractor.getResponseWithId(parameters.getOrFail("id")))
        }
    }
}