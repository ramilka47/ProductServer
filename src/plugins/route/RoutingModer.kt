package com.flower.server.plugins.route

import com.flower.server.database.models.Receipt
import com.flower.server.database.models.Selling
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.helper.*
import com.flower.server.helper.EndPoints.GET_ALL_PAY_ENDPOINT
import com.flower.server.helper.EndPoints.GET_ALL_SELL_ENDPOINT
import com.flower.server.helper.EndPoints.GET_PAY_BY_ID_ENDPOINT
import com.flower.server.helper.EndPoints.GET_SELL_BY_ID_ENDPOINT
import com.flower.server.helper.EndPoints.POST_PAGE_WITH_PAY_OFFSET_ENDPOINT
import com.flower.server.helper.EndPoints.POST_PAGE_WITH_SELL_OFFSET_ENDPOINT
import com.flower.server.models.Sort
import com.flower.server.models.request.PageRequest
import com.flower.server.models.result.PageResponse
import com.flower.server.plugins.interactors.*
import com.google.gson.JsonParseException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*

@KtorDsl
fun Route.RoutingModer() {

    /*Получение списка закупок*/
    get(GET_ALL_PAY_ENDPOINT){
        call.routeModer("get receipts") {
            respondOk(GetAllReceiptInteractor.getResponseWithEmpty())
        }
    }

    /*Получение списка продаж*/
    get(GET_ALL_SELL_ENDPOINT){
        call.routeModer("get sellings") {
            respondOk(GetAllSellingInteractor.getResponseWithEmpty())
        }
    }

    /*Получение закупки*/
    get(GET_PAY_BY_ID_ENDPOINT){
        call.routeModer("get receipt by id") {
            respondOk(GetReceiptByIdInteractor.getResponseWithId(parameters.getOrFail("id")))
        }
    }

    /*Получение продажи*/
    get(GET_SELL_BY_ID_ENDPOINT){
        call.routeModer("get selling by id") {
            respondOk(GetSellingByIdInteractor.getResponseWithId(parameters.getOrFail("id")))
        }
    }

    /*Получение страницы продаж*/
    post(POST_PAGE_WITH_SELL_OFFSET_ENDPOINT) {
        call.routeModer("get page selling") {
            respondOk(
                PostSellingPageInteractor.getResponse(
                    try {
                        gson.fromJson(receiveText(), PageRequest::class.java)
                    } catch (e: JsonParseException) {
                        PageRequest(1, Sort.DEFAULT)
                    }
                )
            )
        }
    }

    /*Получение страницы покупок*/
    post(POST_PAGE_WITH_PAY_OFFSET_ENDPOINT) {
        call.routeModer("get receipt page") {
            respondOk(
                PostReceiptPageInteractor.getResponse(
                    try {
                        gson.fromJson(receiveText(), PageRequest::class.java)
                    } catch (e: JsonParseException) {
                        PageRequest(1, Sort.DEFAULT)
                    }
                )
            )
        }
    }
}