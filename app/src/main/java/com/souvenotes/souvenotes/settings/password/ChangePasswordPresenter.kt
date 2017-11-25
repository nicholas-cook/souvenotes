package com.souvenotes.souvenotes.settings.password

import com.google.firebase.auth.FirebaseAuth
import com.souvenotes.souvenotes.R

/**
 * Created on 11/24/17.
 */
class ChangePasswordPresenter(private var passwordView: IChangePasswordContract.View?) :
        IChangePasswordContract.Presenter {

    private var password = ""

    override fun onPasswordEntered(password: String?) {
        this.password = password ?: ""
        passwordView?.setSubmitButtonEnabled(this.password.isNotEmpty())
    }

    override fun onSubmitButtonClicked() {
        if (password.length < 8) {
            passwordView?.setPasswordError(true, R.string.password_short)
        } else if (!Regex("[A-Za-z]+").containsMatchIn(password)) {
            passwordView?.setPasswordError(true, R.string.password_missing_letter)
        } else if (!Regex("[0-9]+").containsMatchIn(password)) {
            passwordView?.setPasswordError(true, R.string.password_missing_number)
        } else {
            passwordView?.hideKeyboard()
            passwordView?.setPasswordError(false, 0)
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                passwordView?.logout()
            } else {
                user.updatePassword(password).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        passwordView?.onPasswordChanged()
                    } else {
                        passwordView?.onPasswordChangeFailed(R.string.change_password_error)
                    }
                }
            }
        }
    }

    override fun nullifyView() {
        passwordView = null
    }
}