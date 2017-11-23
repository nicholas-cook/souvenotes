package com.souvenotes.souvenotes.settings.reauth

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.LoginModel

/**
 * Created on 11/22/17.
 */
interface IReauthContract {

    interface View {
        fun setEmailError(isError: Boolean, @StringRes message: Int)

        fun setPasswordError(isError: Boolean, @StringRes message: Int)

        fun setSubmitButtonEnabled(enabled: Boolean)

        fun hideKeyboard()

        fun onReauthSuccess()

        fun onReauthFailed(@StringRes message: Int)
    }

    interface Presenter {
        fun onEmailEntered(email: String?)

        fun onPasswordEntered(password: String?)

        fun onSubmitButtonClicked()

        fun setLoginModel(loginModel: LoginModel)

        fun nullifyView()
    }
}