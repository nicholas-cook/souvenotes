package com.souvenotes.souvenotes.settings.password

import android.support.annotation.StringRes

/**
 * Created on 11/24/17.
 */
interface IChangePasswordContract {

    interface View {
        fun setSubmitButtonEnabled(enabled: Boolean)

        fun setPasswordError(isError: Boolean, @StringRes message: Int)

        fun hideKeyboard()

        fun onPasswordChanged()

        fun onPasswordChangeFailed(@StringRes message: Int)

        fun logout()

        fun setProgressBarVisible(visible: Boolean)
    }

    interface Presenter {
        fun onPasswordEntered(password: String?)

        fun onSubmitButtonClicked()

        fun nullifyView()
    }
}