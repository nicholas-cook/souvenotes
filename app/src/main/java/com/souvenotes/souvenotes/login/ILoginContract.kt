package com.souvenotes.souvenotes.login

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.LoginModel

/**
 * Created on 10/14/17.
 */
interface ILoginContract {

    interface View {
        fun setEmailError(isError: Boolean, @StringRes message: Int)

        fun setPasswordError(isError: Boolean, @StringRes message: Int)

        fun setLoginButtonEnabled(enabled: Boolean)

        fun hideKeyboard()

        fun onLoginSuccess()

        fun onLoginFailed(@StringRes message: Int)

        fun setProgressBarVisible(visible: Boolean)
    }

    interface Presenter {
        fun onEmailEntered(email: String?)

        fun onPasswordEntered(password: String?)

        fun onLoginButtonClicked()

        fun setLoginModel(loginModel: LoginModel)

        fun nullifyView()
    }
}