package com.souvenotes.souvenotes.settings.reauth

import com.google.firebase.auth.*
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.LoginModel

/**
 * Created by nicholascook on 11/22/17.
 */
class ReauthPresenter(private var reauthView: IReauthContract.View?) :
        IReauthContract.Presenter {

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
        reauthView?.setSubmitButtonEnabled(
                loginModel.email.isNotBlank() && loginModel.password.isNotEmpty())
    }

    override fun onSubmitButtonClicked() {
        val credential = EmailAuthProvider.getCredential(loginModel.email, loginModel.password)
        FirebaseAuth.getInstance().currentUser?.reauthenticate(
                credential)?.addOnCompleteListener { result ->
            if (result.isSuccessful) {
                reauthView?.onReauthSuccess()
            } else {
                when {
                    result.exception is FirebaseAuthInvalidUserException -> reauthView?.onReauthFailed(
                            R.string.error_account)
                    result.exception is FirebaseAuthInvalidCredentialsException -> reauthView?.onReauthFailed(
                            R.string.error_credentials)
                    else -> reauthView?.onReauthFailed(R.string.reauth_error)
                }
            }
        }
    }

    override fun setLoginModel(loginModel: LoginModel) {
        this.loginModel = loginModel
    }

    override fun nullifyView() {
        reauthView = null
    }
}