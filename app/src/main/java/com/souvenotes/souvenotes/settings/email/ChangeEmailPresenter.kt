package com.souvenotes.souvenotes.settings.email

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.souvenotes.souvenotes.R

/**
 * Created on 11/24/17.
 */
class ChangeEmailPresenter(private var emailView: IChangeEmailContract.View?) :
        IChangeEmailContract.Presenter {

    private var email = ""

    override fun onEmailEntered(email: String?) {
        this.email = email ?: ""
        emailView?.setSubmitButtonEnabled(this.email.isNotBlank())
    }

    override fun onSubmitButtonClicked() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailView?.setEmailError(true, R.string.email_format)
        } else {
            emailView?.setEmailError(false, 0)
            updateEmail()
        }
    }

    private fun updateEmail() {
        emailView?.hideKeyboard()
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            emailView?.logout()
        } else {
            user.updateEmail(email).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    emailView?.onEmailChanged()
                } else {
                    when {
                        result.exception is FirebaseAuthUserCollisionException -> emailView?.onEmailChangeFailed(
                                R.string.email_exists)
                        else -> emailView?.onEmailChangeFailed(R.string.change_email_error)
                    }
                }
            }
        }
    }

    override fun nullifyView() {
        emailView = null
    }
}