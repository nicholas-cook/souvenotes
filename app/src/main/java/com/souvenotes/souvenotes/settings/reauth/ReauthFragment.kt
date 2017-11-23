package com.souvenotes.souvenotes.settings.reauth

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.LoginModel
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_reauth.*
import kotlinx.android.synthetic.main.fragment_reauth.view.*

/**
 * Created by nicholascook on 11/22/17.
 */
class ReauthFragment : Fragment(), IReauthContract.View {

    private var reauthPresenter: IReauthContract.Presenter? = null
    private var loginModel = LoginModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reauth, container, false)

        loginModel = savedInstanceState?.getParcelable(
                EXTRA_LOGIN_MODEL) ?: LoginModel()
        reauthPresenter = ReauthPresenter(this)

        view.reauth_email.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                reauthPresenter?.onEmailEntered(s.toString())
            }
        })
        reauth_email.setText(loginModel.email)

        view.reauth_password.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                reauthPresenter?.onPasswordEntered(s.toString())
            }
        })
        reauth_password.setText(loginModel.password)

        view.button_submit.setOnClickListener {
            reauthPresenter?.onSubmitButtonClicked()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
                EXTRA_LOGIN_MODEL, loginModel)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        reauthPresenter?.nullifyView()
    }

    override fun setEmailError(isError: Boolean, message: Int) {
        if (isError) {
            reauth_email.error = getString(message)
            reauth_email.requestFocus()
        } else {
            reauth_email.error = null
        }
    }

    override fun setPasswordError(isError: Boolean, message: Int) {
        if (isError) {
            reauth_password.error = getString(message)
            reauth_password.requestFocus()
        } else {
            reauth_password.error = null
        }
    }

    override fun setSubmitButtonEnabled(enabled: Boolean) {
        button_submit.isEnabled = enabled
    }

    override fun hideKeyboard() {
        hideKeyboard(reauth_parent)
    }

    override fun onReauthSuccess() {
        //TODO: Direct to next fragment
    }

    override fun onReauthFailed(message: Int) {
        Snackbar.make(reauth_parent, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val EXTRA_LOGIN_MODEL = "com.souvenotes.souvenotes.account.EXTRA_LOGIN_MODEL"
    }
}