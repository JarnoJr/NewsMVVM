package com.example.newsmvvm.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsmvvm.util.Constants.DEFAULT_CATEGORY
import com.example.newsmvvm.util.Constants.DEFAULT_COUNTRY
import com.example.newsmvvm.util.Constants.NEWS_DATASTORE
import com.example.newsmvvm.util.Constants.SELECTED_CATEGORY
import com.example.newsmvvm.util.Constants.SELECTED_COUNTRY
import com.example.newsmvvm.util.PreferencesManager.PreferencesKeys.CATEGORY
import com.example.newsmvvm.util.PreferencesManager.PreferencesKeys.COUNTRY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

data class FilterPreferences(
    val country: String,
    val category: String,
)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NEWS_DATASTORE)

    private val dataStore = context.dataStore

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val country = preferences[COUNTRY] ?: DEFAULT_COUNTRY
            val category = preferences[CATEGORY] ?: DEFAULT_CATEGORY
            FilterPreferences(country, category)
        }

    suspend fun updateCountry(country: String) {
        dataStore.edit {
            it[COUNTRY] = country
        }
    }

    suspend fun updateCategory(category: String) {
        dataStore.edit {
            it[CATEGORY] = category
        }
    }

    private object PreferencesKeys {
        val COUNTRY = stringPreferencesKey(SELECTED_COUNTRY)
        val CATEGORY = stringPreferencesKey(SELECTED_CATEGORY)
    }
}