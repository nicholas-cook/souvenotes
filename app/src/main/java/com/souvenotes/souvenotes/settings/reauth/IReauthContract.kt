package com.souvenotes.souvenotes.settings.reauth

import android.support.annotation.StringRes

/**
 * Created on 11/22/17.
 */
interface IReauthContract {

    interface View {
        fun setPasswordError(isError: Boolean, @StringRes message: Int)

        fun setSubmitButtonEnabled(enabled: Boolean)

        fun hideKeyboard()

        fun onReauthSuccess()

        fun onReauthFailed(@StringRes message: Int)

        fun logout()

        fun setProgressBarVisible(visible: Boolean)
    }

    interface Presenter {
        fun onPasswordEntered(password: String?)

        fun onSubmitButtonClicked()

        fun nullifyView()
    }
}