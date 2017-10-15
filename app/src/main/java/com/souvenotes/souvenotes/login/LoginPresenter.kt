package com.souvenotes.souvenotes.login

import android.util.Patterns
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.LoginModel

/**
 * Created by NicholasCook on 10/14/17.
 */
class LoginPresenter(private val loginView: ILoginContract.View?) : ILoginContract.Presenter {

    private var loginModel = LoginModel()

    override fun onEmailEntered(email: String?) {
        loginModel.email = email ?: ""
        validateModel()
    }

    override fun onPasswordEntered(password: String?) {
        loginModel.password = password ?: ""
        validateModel()
    }

    private fun validateModel() {
        loginView?.setLoginButtonEnabled(loginModel.email.isNotBlank() && loginModel.password.isNotEmpty())
    }

    override fun onLoginButtonClicked() {
        var valid = true
        if (!Patterns.EMAIL_ADDRESS.matcher(loginModel.email).matches()) {
            valid = false
            loginView?.setEmailError(true, R.string.email_format)
        } else {
            loginView?.setEmailError(false, 0)
        }
        if (loginModel.password.isEmpty()) {
            valid = false
            loginView?.setPasswordError(true, R.string.password_empty)
        } else {
            loginView?.setPasswordError(false, 0)
        }
        if (valid) {
            loginView?.logInUser(loginModel.email, loginModel.password)
        }
    }

    override fun setLoginModel(loginModel: LoginModel) {
        this.loginModel = loginModel
    }
}