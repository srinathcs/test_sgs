package com.example.myapplication1.mypreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(MainPreference.PREF_NAME)

class MainPreference(val context: Context) {

    companion object {

        const val PREF_NAME = "main_pref"
        const val CID = "cid"
        const val COMPANY_NAME = "company_name"
        const val LEDGER_ID = "ledger_id"
        const val MOBILE_NO = "mobile_no"
        const val PHONE = "phone"
        const val ROLE_ID = "role_id"
        const val ROLE_TYPE = "role_type"
        const val SSID = "ssid"
        const val USER_ID = "user_id"
        const val F_NAME = "f_name"
        const val NAME = "name"
        const val IS_LOGGED_IN = "is_logged_in"
        const val COMPANY_ID = "company_id"
        const val CUSTOMER_ID = "customer_id"
        const val ICON = "icon"
        const val COMPANY_SERIAL_ID = "company_serial_id"
        const val PHONE_NO = "phone_no"
        const val USER_PHONE = "user_Phone"
        const val EMAIL = "email"
        const val PAY_ID = "payid"

        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val DATE = "date"

        const val TEXTVIEW = "textview"

        const val ROUTE_ID = "route_id"

        const val BID = "bid"

    }

    suspend fun saveIconStatus(value: String) {

        val dataStoreKey = stringPreferencesKey(ICON)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

}