package com.gtoretti.buffet.data

import android.content.Context

import com.gtoretti.buffet.data.balanceSheet.BalanceSheetDao

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

}
