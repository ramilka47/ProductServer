package com.flower.server.plugins.route

import com.flower.server.helper.execeptions.ExistException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.helper.*
import com.flower.server.helper.EndPoints.GET_ALL_CHEQUE_ENDPOINT
import com.flower.server.helper.EndPoints.GET_ALL_LOSS_ENDPOINT
import com.flower.server.helper.EndPoints.GET_ALL_USER_ENDPOINT
import com.flower.server.helper.EndPoints.GET_LOSS_BY_ID_ENDPOINT
import com.flower.server.helper.EndPoints.POST_PAGE_WITH_LOSS_OFFSET_ENDPOINT
import com.flower.server.helper.EndPoints.GET_USER_BY_ID_ENDPOINT
import com.flower.server.helper.EndPoints.POST_ADD_CATEGORY_ENDPOINT
import com.flower.server.helper.EndPoints.POST_ADD_LOSS_ENDPOINT
import com.flower.server.helper.EndPoints.POST_ADD_USER_ENDPOINT
import com.flower.server.models.Sort
import com.flower.server.models.request.*
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

@KtorDsl
fun Route.RoutingGod() {

    /*Получение конкретной потери*/
    get(GET_LOSS_BY_ID_ENDPOINT){
        call.routeGod("get losses by Id") {
            respondOk(GetLossByIdInteractor.getResponseWithId(parameters.getOrFail("id")))
        }
    }

    /*получение списка потерь*/
    get(GET_ALL_LOSS_ENDPOINT){
        call.routeGod("get losses") {
            respondOk(GetAllLossInteractor.getResponseWithEmpty())
        }
    }

    /*Получение страницы потерь*/
    post(POST_PAGE_WITH_LOSS_OFFSET_ENDPOINT) {
        call.routeGod("get losses page") {
            respondOk(
                PostLossPageInteractor
                    .getResponse(
                        try {
                            gson.fromJson(receiveText(), PageRequest::class.java)
                        } catch (e: JsonParseException) {
                            PageRequest(1, Sort.DEFAULT)
                        }
                    )
            )
        }
    }

    /*get user by id*/
    get(GET_USER_BY_ID_ENDPOINT){
        call.routeGod("get user by Id") {
            respondOk(GetUserByIdInteractor.getResponseWithId(parameters.getOrFail("id")))
        }
    }

    /*get Users*/
    get(GET_ALL_USER_ENDPOINT){
        call.routeGod("get users by Id") {
            respondOk(GetAllUserInteractor.getResponseWithEmpty())
        }
    }

    /*Добавление категории*/
    post(POST_ADD_CATEGORY_ENDPOINT) {
        call.routeGod("get cheques"){
            respondOk(PostAddCategoryInteractor.getResponse(
                gson.fromJson(receiveText(), AddCategoryRequest::class.java)
            ))
        }
    }

    /*Добавление юзера*/
    post(POST_ADD_USER_ENDPOINT){
        call.routeGod("add user"){
            respondOk(PostAddUserInteractor.getResponse(gson.fromJson(receiveText(), AddUserRequest::class.java)))
        }
    }

    /*Получение чеков*/
    get(GET_ALL_CHEQUE_ENDPOINT){
        call.routeGod("get cheques"){
            respondOk(GetAllChequeInteractor.getResponseWithEmpty())
        }
    }

    /*Добавление потери*/
    post(POST_ADD_LOSS_ENDPOINT){
        call.routeGod("add loss"){
            respondOk(PostAddLossInteractor.getResponse(
                gson.fromJson(receiveText(), OperationProductsRequest::class.java)
            ))
        }
    }

    /*Добавление чека*/

    /*Обновление чека*/

    /*Добавление покупки
    post("/receipts"){
        call.routeGod("add receipt"){
            //todo
        }
    }

    Обновление покупки
    post("/receipts"){
        call.routeGod("add receipt"){
            //todo
        }
    }

    Добавление продажи
    post("/sellings"){
        call.routeGod("add selling"){
            //todo
        }
    }

    Обновление продажи
    post("/sell/products/{id}") {
        call.routeAdmin("update selling"){
            //todo
        }
    }*/

}