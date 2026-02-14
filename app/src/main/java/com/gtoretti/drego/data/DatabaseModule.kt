package com.gtoretti.drego.data

import android.content.Context
import com.gtoretti.drego.data.accountingAccounts.AccountingAccountDao
import com.gtoretti.drego.data.equityChangesStatement.EquityChangesStatementDao
import com.gtoretti.drego.data.balanceSheet.BalanceSheetDao
import com.gtoretti.drego.data.cashFlowsStatement.CashFlowsStatementDao
import com.gtoretti.drego.data.explanatoryNotes.ExplanatoryNoteDao
import com.gtoretti.drego.data.periodResultStatement.PeriodResultStatementDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideBalanceSheetDao(appDatabase: AppDatabase): BalanceSheetDao {
        return appDatabase.balanceSheetDao()
    }

    @Provides
    fun provideAccountingAccountDao(appDatabase: AppDatabase): AccountingAccountDao {
        return appDatabase.accountingAccountDao()
    }

    @Provides
    fun provideEquityChangesStatementDao(appDatabase: AppDatabase): EquityChangesStatementDao {
        return appDatabase.equityChangesStatementDao()
    }

    @Provides
    fun provideCashFlowsStatementDao(appDatabase: AppDatabase): CashFlowsStatementDao {
        return appDatabase.cashFlowsStatementDao()
    }

    @Provides
    fun provideExplanatoryNoteDao(appDatabase: AppDatabase): ExplanatoryNoteDao {
        return appDatabase.explanatoryNoteDao()
    }

    @Provides
    fun providePeriodResultStatementDao(appDatabase: AppDatabase): PeriodResultStatementDao {
        return appDatabase.periodResultStatementDao()
    }
}
