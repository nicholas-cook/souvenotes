package com.souvenotes.souvenotes.settings.email

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.login.LoginActivity
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_change_email.*

/**
 * Created on 11/24/17.
 */
class ChangeEmailFragment : Fragment(), IChangeEmailContract.View {

    private var emailPresenter: IChangeEmailContract.Presenter? = null
    private var email = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        emailPresenter = ChangeEmailPresenter(this)

        email = savedInstanceState?.getString(EXTRA_EMAIL) ?: ""

        return inflater.inflate(R.layout.fragment_change_email, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        change_email.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                email = s.toString()
                emailPresenter?.onEmailEntered(email)
            }
        })
        change_email.setText(email)

        button_submit.setOnClickListener {
            emailPresenter?.onSubmitButtonClicked()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_EMAIL, email)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        emailPresenter?.nullifyView()
    }

    override fun setSubmitButtonEnabled(enabled: Boolean) {
        button_submit.isEnabled = enabled
    }

    override fun setEmailError(isError: Boolean, message: Int) {
        if (isError) {
            change_email.error = getString(message)
            change_email.requestFocus()
        } else {
            change_email.error = null
        }
    }

    override fun hideKeyboard() {
        hideKeyboard(change_email_parent)
    }

    override fun onEmailChanged() {
        Toast.makeText(activity, R.string.email_change_success, Toast.LENGTH_LONG).show()
        activity.finish()
    }

    override fun onEmailChangeFailed(message: Int) {
        Snackbar.make(change_email_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun logout() {
        Toast.makeText(activity, R.string.log_back_in, Toast.LENGTH_LONG).show()
        LoginActivity.logout(activity)
    }

    companion object {
        private const val EXTRA_EMAIL = "com.souvenotes.souvenotes.settings.email.EXTRA_EMAIL"

        fun newInstance(): ChangeEmailFragment = ChangeEmailFragment()
    }
}