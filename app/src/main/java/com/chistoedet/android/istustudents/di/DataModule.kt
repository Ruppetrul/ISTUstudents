package com.chistoedet.android.istustudents.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import javax.inject.Inject
import javax.inject.Named


@Module()
class DataModule @Inject constructor(var context: Context) {

     private val PREF_FILE_NAME = "ISTU_SHARED_PREFERENCES"

     @Provides
     @Singleton
     open fun provideSharedPreference(): SharedPreferences? {
         return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
     }
}

