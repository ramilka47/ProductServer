package com.flower.server.web.models.response.bonus_club

data class BonusCard(val id : Long,
                     val name : String,
                     val active : Boolean,
                     val count : Double)