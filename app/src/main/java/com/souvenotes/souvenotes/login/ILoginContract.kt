package com.souvenotes.souvenotes.login

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.LoginModel

/**
 * Created by NicholasCook on 10/14/17.
 */
interface ILoginContract {

    interface View {
        fun setEmailError(isError: Boolean, @StringRes message: Int)

        fun setPasswordError(isError: Boolean, @StringRes message: Int)

        fun logInUser(email: String, password: String)

        fun setLoginButtonEnabled(enabled: Boolean)
    }

    interface Presenter {
        fun onEmailEntered(email: String?)

        fun onPasswordEntered(password: String?)

        fun onLoginButtonClicked()

        fun setLoginModel(loginModel: LoginModel)
    }
}