/*
 */

package com.gtoretti.drego.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gtoretti.drego.data.accountingAccounts.AccountingAccount
import com.gtoretti.drego.data.accountingAccounts.AccountingAccountDao
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatement
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatementDao
import com.gtoretti.drego.data.balanceSheet.BalanceSheet
import com.gtoretti.drego.data.balanceSheet.BalanceSheetDao
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowStatement
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowStatementItem
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowsStatementDao
import com.gtoretti.drego.data.balanceSheet.BalanceSheetItem
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatementItem
import com.gtoretti.drego.data.explanatoryNotes.ExplanatoryNote
import com.gtoretti.drego.data.explanatoryNotes.ExplanatoryNoteDao
import com.gtoretti.drego.data.periodResultStatement.PeriodResultStatement
import com.gtoretti.drego.data.periodResultStatement.PeriodResultStatementDao
import com.gtoretti.drego.data.periodResultStatement.PeriodResultStatementItem
import com.gtoretti.drego.utils.DATABASE_NAME


/**
 * The Room database for this app
 */



@Database(entities = [BalanceSheet::class, BalanceSheetItem::class, AccountingAccount::class, EquityChangesStatement::class, EquityChangesStatementItem::class, CashFlowStatement::class, CashFlowStatementItem::class, PeriodResultStatement::class, PeriodResultStatementItem::class, ExplanatoryNote::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun balanceSheetDao(): BalanceSheetDao
    abstract fun accountingAccountDao(): AccountingAccountDao
    abstract fun equityChangesStatementDao(): EquityChangesStatementDao
    abstract fun explanatoryNoteDao(): ExplanatoryNoteDao
    abstract fun periodResultStatementDao(): PeriodResultStatementDao
    abstract fun cashFlowsStatementDao(): CashFlowsStatementDao



        companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                        }
                    }
                )
                .build()
        }
    }
}
