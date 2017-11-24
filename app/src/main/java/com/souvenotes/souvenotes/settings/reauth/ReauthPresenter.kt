package com.souvenotes.souvenotes.settings.reauth

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.souvenotes.souvenotes.R

/**
 * Created on 11/22/17.
 */
class ReauthPresenter(private var reauthView: IReauthContract.View?) :
        IReauthContract.Presenter {

    private var password: String = ""

    override fun onPasswordEntered(password: String?) {
        this.password = password ?: ""
        validateModel()
    }

    private fun validateModel() {
        reauthView?.setSubmitButtonEnabled(password.isNotEmpty())
    }

    override fun onSubmitButtonClicked() {
        reauthView?.hideKeyboard()
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            reauthView?.logout()
        } else {
            val credential = EmailAuthProvider.getCredential(user.email ?: "", password)
            user.reauthenticate(credential).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    reauthView?.onReauthSuccess()
                } else {
                    when {
                        result.exception is FirebaseAuthInvalidCredentialsException -> reauthView?.onReauthFailed(R.string.error_credentials)
                        else -> reauthView?.onReauthFailed(R.string.reauth_error)
                    }
                }
            }
        }
    }

    override fun nullifyView() {
        reauthView = null
    }
}