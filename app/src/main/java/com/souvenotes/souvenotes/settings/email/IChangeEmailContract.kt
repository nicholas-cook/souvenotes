package com.souvenotes.souvenotes.settings.email

import android.support.annotation.StringRes

/**
 * Created on 11/24/17.
 */
interface IChangeEmailContract {

    interface View {
        fun setSubmitButtonEnabled(enabled: Boolean)

        fun setEmailError(isError: Boolean, @StringRes message: Int)

        fun hideKeyboard()

        fun onEmailChanged()

        fun onEmailChangeFailed(@StringRes message: Int)

        fun logout()
    }

    interface Presenter {
        fun onEmailEntered(email: String?)

        fun onSubmitButtonClicked()

        fun nullifyView()
    }
}