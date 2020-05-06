package com.example.ctc.pref

import android.content.SharedPreferences
import com.example.ctc.base.prefers.PreferenceHelper
import com.example.ctc.base.prefers.get
import com.example.ctc.base.prefers.set
import com.example.ctc.extensions.AppContext
import com.example.ctc.login.model.Name

/**
 * @author by hunghoang on 7/21/18.
 */
class AppPrefs {
    private val pref: SharedPreferences

    init {
        pref = PreferenceHelper.newPrefs(AppContext, SHARE_PREF_NAME)
    }

    companion object {
        private const val SHARE_PREF_NAME = "App.Pref"
        private const val SHARE_PREF_USER = "App.Pref.user"
        private const val KEY_TOKEN = "Key.Token"
        @Volatile
        private var INSTANCE: AppPrefs? = null

        fun getInstance() =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: AppPrefs().also {
                        INSTANCE = it
                    }
                }
    }

    var name: Name?
        get() = pref[SHARE_PREF_USER]
        set(value) {
            if (value != null) {
                pref[SHARE_PREF_USER] = value
            } else {
                pref.edit().remove(SHARE_PREF_USER).apply()
            }
        }
}