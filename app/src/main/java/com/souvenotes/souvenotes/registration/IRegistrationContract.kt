package com.souvenotes.souvenotes.registration

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.RegistrationModel
import com.souvenotes.souvenotes.utils.IBasePresenter

/**
 * Created by NicholasCook on 10/11/17.
 */
interface IRegistrationContract {

    interface View {
        fun setRegisterButtonEnabled(enabled: Boolean)

        fun loadNotesListActivity()

        fun showRegistrationError()

        fun setEmailError()

        fun setPasswordError(@StringRes message: Int)
    }

    interface Presenter : IBasePresenter {
        fun setRegistrationModel(model: RegistrationModel)

        fun onEmailEntered(email: String?)

        fun onPasswordEntered(password: String?)

        fun onRegisterButtonClicked()
    }
}