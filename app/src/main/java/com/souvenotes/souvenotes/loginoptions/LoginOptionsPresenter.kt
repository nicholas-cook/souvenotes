package com.souvenotes.souvenotes.loginoptions

/**
 * Created by NicholasCook on 10/10/17.
 */
class LoginOptionsPresenter(private val optionsView: ILoginOptionsContract.View?) : ILoginOptionsContract.Presenter {

    override fun start() {
    }

    override fun stop() {
    }

    override fun onLoginClicked() {
        optionsView?.loadLoginActivity()
    }

    override fun onRegisterClicked() {
        optionsView?.loadRegistrationActivity()
    }
}