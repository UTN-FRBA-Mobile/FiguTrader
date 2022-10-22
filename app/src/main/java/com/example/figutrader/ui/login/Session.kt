package com.example.figutrader.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings.Global.getString
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.UserProfile
import com.example.figutrader.MainActivity
import com.example.figutrader.R
import javax.security.auth.callback.Callback

class Session(cntx: Context?) {
    private val prefs: SharedPreferences
    private var mainBinding: MainActivity?= null

    fun setusername(username: String?) {
        prefs.edit().putString("username", username).commit()
    }

    fun getusername(): String? {
        return prefs.getString("username", "")
    }

    init {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }

    private fun showUserProfile() {
        // Guard against showing the profile when no user is logged in
        if (mainBinding?.cachedCredentials == null) {
            return
        }

        val client = AuthenticationAPIClient(mainBinding!!.account)
        client
            .userInfo(mainBinding!!.cachedCredentials!!.accessToken!!)
            .start(object : Callback<UserProfile, AuthenticationException> {

                fun onFailure(exception: AuthenticationException) {
                    showSnackBar(getString(
                        R.string.general_failure_with_exception_code,
                        exception.getCode()))
                }

                fun onSuccess(profile: UserProfile) {
                    mainBinding?.cachedUserProfile = profile
                }

            })
    }
}