package com.souvenotes.souvenotes.loginoptions

import com.souvenotes.souvenotes.utils.IBasePresenter

/**
 * Created by NicholasCook on 10/10/17.
 */
interface ILoginOptionsContract {

    interface View {
        fun loadLoginActivity()

        fun loadRegistrationActivity()
    }

    interface Presenter : IBasePresenter {
        fun onLoginClicked()

        fun onRegisterClicked()
    }
}