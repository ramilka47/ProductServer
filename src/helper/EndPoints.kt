package com.flower.server.helper

object EndPoints {

    const val GET_LOSS_BY_ID_ENDPOINT = "/losses/{id}"
    const val GET_ALL_LOSS_ENDPOINT = "/losses"
    const val POST_PAGE_WITH_LOSS_OFFSET_ENDPOINT = "/losses/request"
    const val POST_ADD_LOSS_ENDPOINT = "/losses"

    const val GET_USER_BY_ID_ENDPOINT = "/users/{id}"
    const val GET_ALL_USER_ENDPOINT = "/users"
    const val POST_ADD_USER_ENDPOINT = "/users"

    const val POST_ADD_CATEGORY_ENDPOINT = "/category"
    const val GET_ALL_CATEGORY_ENDPOINT = "/categories"
    const val GET_ALL_PRODUCT_BY_CATEGORY_ENDPOINT = "/categories/{id}"

    const val POST_ADD_PRODUCT_ENDPOINT = "/products"
    const val GET_PRODUCT_BY_ID_ENDPOINT = "/products/{id}"
    const val GET_PRODUCT_BY_CODE_ENDPOINT = "/products/{code}"
    const val POST_UPDATE_PRODUCT_ENDPOINT = "/products/{id}"
    const val POST_SELL_PRODUCT_ENDPOINT = "/sell/products"
    const val POST_CANCEL_SELL_PRODUCT_ENDPOINT = "/sell/products/{id}"
    const val POST_PAY_PRODUCT_ENDPOINT = "/sell/products"
    const val GET_SEARCH_RESULT_PRODUCT_BY_NAME_ENDPOINT = "/search/products"
    const val GET_ALL_PRODUCT_ENDPOINT = "/products/request"
    const val POST_PAGE_WITH_PRODUCT_OFFSET_ENDPOINT = "/products/request"

    const val GET_ALL_PAY_ENDPOINT = "/receipts/request"
    const val GET_PAY_BY_ID_ENDPOINT = "/receipts/{id}"
    const val POST_PAGE_WITH_PAY_OFFSET_ENDPOINT = "/receipts/request"

    const val GET_ALL_SELL_ENDPOINT = "/sellings/request"
    const val GET_SELL_BY_ID_ENDPOINT = "/sellings/{id}"
    const val POST_PAGE_WITH_SELL_OFFSET_ENDPOINT = "/sellings/request"

    const val GET_ALL_CHEQUE_ENDPOINT = "/cheques"

    const val POST_AUTH_LOGIN_ENDPOINT = "/login"
    const val GET_PHOTO_ENDPOINT = "/photo/{id}"

}