package com.chistoedet.android.istustudents.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.Context
import javax.inject.Inject



@Module
class DataModule @Inject constructor(var context: Context) {

     private val PREFNAME = "ISTU_SHARED_PREFERENCES"

     @Provides
     @Singleton
     open fun provideSharedPreference(): SharedPreferences? {
         return context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE)
     }
}

