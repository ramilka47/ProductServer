package com.flower.server.helper

import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.core.security.ISecurity
import com.flower.server.core.security.ImplSecurity
import com.flower.server.database.dao.ICrmDao
import com.flower.server.database.dao.IDaoCatalog
import com.flower.server.database.dao.IStockroomDao
import com.flower.server.database.dao.impl.CatalogDaoImpl
import com.flower.server.database.dao.impl.CrmDaoImpl
import com.flower.server.database.dao.impl.StockroomDaoImpl
import com.google.gson.Gson

val gson = Gson()

val generator : IGenerator = Generator()

val security : ISecurity = ImplSecurity()

val daoCatalog : IDaoCatalog = CatalogDaoImpl()

val daoCrm : ICrmDao = CrmDaoImpl()

val daoStockroom : IStockroomDao = StockroomDaoImpl()