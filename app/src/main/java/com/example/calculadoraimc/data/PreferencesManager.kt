package com.example.calculadoraimc.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit

import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val FIRST_TIME_KEY = booleanPreferencesKey("first_time_key")
    }

    suspend fun saveFirstTimeState(isFirstTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[FIRST_TIME_KEY] = isFirstTime
        }
    }

    val isFirstTime: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[FIRST_TIME_KEY] ?: true
        }
}
