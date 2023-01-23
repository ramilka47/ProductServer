package com.flower.server.helper

object Constants {

    const val USER_AGENT_ADDITIVE = "FLOWERSMADI"

    val predicateUserAgent : (String?)->Boolean = { userAgent->
        userAgent?.split("/")?.find { it == USER_AGENT_ADDITIVE} != null
    }

    const val PAGE_SIZE = 100
    const val PAGE_SIZE_LONG = PAGE_SIZE.toLong()

    const val PHOTO_DIRECT = "/photo"

    const val BASE_URL = ""
    const val PHOTO_URL = "$BASE_URL/photo/"
}