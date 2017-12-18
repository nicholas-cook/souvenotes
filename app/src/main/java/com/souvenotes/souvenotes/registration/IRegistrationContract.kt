package com.souvenotes.souvenotes.registration

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.RegistrationModel
import com.souvenotes.souvenotes.utils.IBasePresenter

/**
 * Created on 10/11/17.
 */
interface IRegistrationContract {

    interface View {
        fun setRegisterButtonEnabled(enabled: Boolean)

        fun loadNotesListActivity()

        fun showRegistrationError(@StringRes message: Int)

        fun setEmailError(isError: Boolean, @StringRes message: Int)

        fun setPasswordError(isError: Boolean, @StringRes message: Int)

        fun setPasswordConfirmationError(isError: Boolean, @StringRes message: Int)

        fun registerUser(email: String, password: String)
    }

    interface Presenter : IBasePresenter {
        fun setRegistrationModel(model: RegistrationModel)

        fun onEmailEntered(email: String?)

        fun onPasswordEntered(password: String?)

        fun onPasswordConfirmationEntered(passwordConfirmation: String?)

        fun onRegisterButtonClicked()

        fun nullifyView()
    }
}