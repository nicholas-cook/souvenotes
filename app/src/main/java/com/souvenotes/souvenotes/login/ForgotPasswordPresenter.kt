package com.souvenotes.souvenotes.login

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.souvenotes.souvenotes.R

/**
 * Created on 11/21/17.
 */
class ForgotPasswordPresenter(
    private var forgotView: IForgotPasswordContract.View?) : IForgotPasswordContract.Presenter {

    private var email: String? = null

    override fun onEmailEntered(email: String?) {
        this.email = email
        forgotView?.setResetButtonEnabled(email?.isBlank() == false)
    }

    override fun onResetButtonClicked() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotView?.setEmailError(true, R.string.email_format)
        } else {
            forgotView?.setEmailError(false, 0)
            forgotView?.hideKeyboard()
            val auth = FirebaseAuth.getInstance()
            auth.useAppLanguage()
            auth.sendPasswordResetEmail(
                email ?: "").addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    forgotView?.onEmailSent()
                } else {
                    when {
                        result.exception is FirebaseAuthInvalidUserException -> forgotView?.onResetFailed(
                            R.string.error_account)
                        else -> forgotView?.onResetFailed(R.string.reset_password_error)
                    }
                }
            }
        }
    }

    override fun nullifyView() {
        forgotView = null
    }
}