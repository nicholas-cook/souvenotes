package com.souvenotes.souvenotes.registration

import android.util.Patterns
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.RegistrationModel

/**
 * Created by NicholasCook on 10/11/17.
 */
class RegistrationPresenter(private var view: IRegistrationContract.View?) :
    IRegistrationContract.Presenter {

    private var model = RegistrationModel()

    override fun start() {
    }

    override fun stop() {
    }

    override fun setRegistrationModel(model: RegistrationModel) {
        this.model = model
    }

    override fun onEmailEntered(email: String?) {
        model.email = email ?: ""
        validateModel()
    }

    override fun onPasswordEntered(password: String?) {
        model.password = password ?: ""
        validateModel()
    }

    override fun onPasswordConfirmationEntered(passwordConfirmation: String?) {
        model.passwordConfirmation = passwordConfirmation ?: ""
        validateModel()
    }

    private fun validateModel() {
        view?.setRegisterButtonEnabled(model.email.isNotBlank() && model.password.isNotEmpty()
            && model.passwordConfirmation.isNotEmpty())
    }

    override fun onRegisterButtonClicked() {
        var valid = true
        if (!Patterns.EMAIL_ADDRESS.matcher(model.email).matches()) {
            valid = false
            view?.setEmailError(true, R.string.email_format)
        } else {
            view?.setEmailError(false, 0)
        }
        if (model.password.length < 8) {
            valid = false
            view?.setPasswordError(true, R.string.password_short)
        } else if (!Regex("[A-Za-z]+").containsMatchIn(model.password)) {
            valid = false
            view?.setPasswordError(true, R.string.password_missing_letter)
        } else if (!Regex("[0-9]+").containsMatchIn(model.password)) {
            valid = false
            view?.setPasswordError(true, R.string.password_missing_number)
        } else {
            view?.setPasswordError(false, 0)
        }
        if (model.passwordConfirmation != model.password) {
            valid = false
            view?.setPasswordConfirmationError(true, R.string.password_match_error)
        } else {
            view?.setPasswordConfirmationError(false, 0)
        }
        if (valid) {
            view?.registerUser(model.email, model.password)
        }
    }

    override fun nullifyView() {
        view = null
    }
}