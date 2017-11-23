package com.souvenotes.souvenotes.login.forgotpassword

import android.support.annotation.StringRes

/**
 * Created on 11/21/17.
 */
interface IForgotPasswordContract {

    interface View {
        fun setEmailError(isError: Boolean, @StringRes message: Int)

        fun setResetButtonEnabled(enabled: Boolean)

        fun hideKeyboard()

        fun onEmailSent()

        fun onResetFailed(@StringRes message: Int)
    }

    interface Presenter {
        fun onEmailEntered(email: String?)

        fun onResetButtonClicked()

        fun nullifyView()
    }
}