package com.souvenotes.souvenotes.registration

import com.souvenotes.souvenotes.models.RegistrationModel

/**
 * Created by NicholasCook on 10/11/17.
 */
class RegistrationPresenter(private val view: IRegistrationContract.View?) : IRegistrationContract.Presenter {

    private var model = RegistrationModel()

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    private fun validateModel() {
        view?.setRegisterButtonEnabled(model.email.isNotBlank() && !model.password.isNotBlank())
    }

    override fun onRegisterButtonClicked() {
        //TODO: Validate email and password, begin Firebase registration
    }
}