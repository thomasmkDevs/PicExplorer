package com.pic.explorer.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("picexplorer_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SELECTED_AUTHOR = "selected_author"
    }

    fun saveSelectedAuthor(author: String?) {
        prefs.edit {
            putString(KEY_SELECTED_AUTHOR, author)
        }
    }

    fun getSelectedAuthor(): String? {
        return prefs.getString(KEY_SELECTED_AUTHOR, null)
    }
}