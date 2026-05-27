/*
 */

package com.gtoretti.buffet.data.balanceSheet

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BalanceSheetRepository @Inject constructor(private val balanceSheetDao: BalanceSheetDao) {

    fun getBalanceSheets() = balanceSheetDao.getActiveBalanceSheets()

    suspend fun saveBalanceSheet(balanceSheet: BalanceSheet){
        balanceSheetDao.upsert(balanceSheet)
    }


    suspend fun deleteBalanceSheet(balanceSheet: BalanceSheet){
        balanceSheetDao.delete(balanceSheet.balanceSheetId)
    }


}
