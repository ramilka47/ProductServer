package com.flower.server.plugins.route

import com.flower.server.database.models.CategoryOfProduct
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.helper.*
import com.flower.server.helper.EndPoints.POST_ADD_PRODUCT_ENDPOINT
import com.flower.server.helper.EndPoints.POST_CANCEL_SELL_PRODUCT_ENDPOINT
import com.flower.server.helper.EndPoints.POST_PAY_PRODUCT_ENDPOINT
import com.flower.server.helper.EndPoints.POST_SELL_PRODUCT_ENDPOINT
import com.flower.server.helper.EndPoints.POST_UPDATE_PRODUCT_ENDPOINT
import com.flower.server.models.request.AddProductRequest
import com.flower.server.models.request.OperationProductsRequest
import com.flower.server.models.request.UpdateProductRequest
import com.flower.server.models.result.*
import com.flower.server.plugins.interactors.*
import com.google.gson.JsonArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*
import java.io.BufferedOutputStream
import java.io.File

@KtorDsl
fun Route.RoutingAdmin(){

    /*Добавление товара*/
    post(POST_ADD_PRODUCT_ENDPOINT){
        call.routeAdmin("add product"){
            respondOk(PostAddProductInteractor.getResponse(gson.fromJson(receiveText(), AddProductRequest::class.java)))
        }
    }

    /*Обновление товара*/
    post(POST_UPDATE_PRODUCT_ENDPOINT) {
        call.routeAdmin("update product"){
            respondOk(PostUpdateProductInteractor.getResponse(gson.fromJson(receiveText(), UpdateProductRequest::class.java).apply {
                id = parameters.getOrFail("id").toLong()
            }))
        }
    }

    /*Запрос на покупку товара*/
    post(POST_SELL_PRODUCT_ENDPOINT) {
        call.routeAdmin("sell product"){
            respondOk(PostSellProductInteractor.getResponse(gson.fromJson(receiveText(), OperationProductsRequest::class.java)))
        }
    }

    /*Отмена чека*/
    post(POST_CANCEL_SELL_PRODUCT_ENDPOINT){
        call.routeAdmin("cancel cheque"){
            respondOk(PostCancelSellProductInteractor.getResponseWithId(parameters.getOrFail("id")))
        }
    }

    /*Закупка товара*/
    post(POST_PAY_PRODUCT_ENDPOINT) {
        call.routeAdmin("receipt product") {
            respondOk(PostPayProductInteractor.getResponse(gson.fromJson(receiveText(), OperationProductsRequest::class.java)))
        }
    }

}