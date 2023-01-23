package com.flower.server.helper

import com.flower.server.helper.Constants.BASE_URL
import com.flower.server.helper.Constants.PHOTO_DIRECT
import com.flower.server.helper.Constants.PHOTO_URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun string(temp : String, vararg params : String)=
    String.format(temp, params)

fun photo(id : Any) = "$PHOTO_DIRECT/$id.jpg"

fun photoUrl(id : String) = "$PHOTO_URL/$id"

fun getCurrentTime() : Long =
    DateFormat
        .getTimeInstance()
        .apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.calendar.time.time.apply{
            if (this < 0)
                this * -1
        }

fun getCurrentTimeInSec() : Long = getCurrentTime() / 1000