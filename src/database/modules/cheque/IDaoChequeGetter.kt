package com.flower.server.database.modules.cheque

import com.flower.server.database.models.Cheque

interface IDaoChequeGetter{
    suspend fun cheque(id : Long) : Cheque?
    suspend fun allCheques() : List<Cheque>
}