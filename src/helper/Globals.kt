package com.flower.server.helper

import com.flower.server.database.impl.DaoFacadeImpl
import com.flower.server.database.modules.category.IDaoCategory
import com.flower.server.database.modules.category.IDaoCategoryGetter
import com.flower.server.database.modules.category.IDaoCategorySetter
import com.flower.server.database.modules.categoryOfProducts.IDaoCategoryOfProducts
import com.flower.server.database.modules.categoryOfProducts.IDaoCategoryOfProductsGetter
import com.flower.server.database.modules.categoryOfProducts.IDaoCategoryOfProductsSetter
import com.flower.server.database.modules.cheque.IDaoCheque
import com.flower.server.database.modules.cheque.IDaoChequeGetter
import com.flower.server.database.modules.cheque.IDaoChequeSetter
import com.flower.server.database.modules.loss.IDaoLoss
import com.flower.server.database.modules.loss.IDaoLossGetter
import com.flower.server.database.modules.loss.IDaoLossSetter
import com.flower.server.database.modules.products.IDaoProducts
import com.flower.server.database.modules.products.IDaoProductsGetter
import com.flower.server.database.modules.products.IDaoProductsSetter
import com.flower.server.database.modules.receipt.IDaoReceipt
import com.flower.server.database.modules.receipt.IDaoReceiptGetter
import com.flower.server.database.modules.receipt.IDaoReceiptSetter
import com.flower.server.database.modules.selling.IDaoSelling
import com.flower.server.database.modules.selling.IDaoSellingGetter
import com.flower.server.database.modules.selling.IDaoSellingSetter
import com.flower.server.database.modules.user.IDaoUser
import com.flower.server.database.modules.user.IDaoUserGetter
import com.flower.server.database.modules.user.IDaoUserSetter
import com.flower.server.plugins.generate.Generator
import com.flower.server.plugins.generate.IGenerator
import com.google.gson.Gson

val gson = Gson()

val generator : IGenerator = Generator()

private val dao : DaoFacadeImpl = DaoFacadeImpl()

private val daoReceipt : IDaoReceipt = dao
private val daoLoss : IDaoLoss = dao
private val daoProducts : IDaoProducts = dao
private val daoUser : IDaoUser = dao
private val daoSelling : IDaoSelling = dao
private val daoCategory : IDaoCategory = dao
private val daoCategoryOfProducts : IDaoCategoryOfProducts = dao
private val daoCheque : IDaoCheque = dao

val daoSellingGetter : IDaoSellingGetter = daoSelling
val daoSellingSetter : IDaoSellingSetter = daoSelling
val daoReceiptGetter : IDaoReceiptGetter = daoReceipt
val daoReceiptSetter : IDaoReceiptSetter = daoReceipt
val daoLossGetter : IDaoLossGetter = daoLoss
val daoLossSetter : IDaoLossSetter = daoLoss
val daoProductsGetter : IDaoProductsGetter = daoProducts
val daoProductsSetter : IDaoProductsSetter = daoProducts
val daoUserGetter : IDaoUserGetter = daoUser
val daoUserSetter : IDaoUserSetter = daoUser
val daoCategoryGetter : IDaoCategoryGetter = daoCategory
val daoCategorySetter : IDaoCategorySetter = daoCategory
val daoCategoryOfProductsGetter : IDaoCategoryOfProductsGetter = daoCategoryOfProducts
val daoCategoryOfProductsSetter : IDaoCategoryOfProductsSetter = daoCategoryOfProducts
val daoChequeGetter : IDaoChequeGetter = daoCheque
val daoChequeSetter : IDaoChequeSetter = daoCheque