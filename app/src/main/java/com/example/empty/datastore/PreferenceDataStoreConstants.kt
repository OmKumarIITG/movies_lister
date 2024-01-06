package com.example.empty.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {
    val MOVIE_ID_KEY = intPreferencesKey("MOVIE_ID_KEY")
    val MOVIE_TITLE_KEY = stringPreferencesKey("MOVIE_TITLE_KEY")
}