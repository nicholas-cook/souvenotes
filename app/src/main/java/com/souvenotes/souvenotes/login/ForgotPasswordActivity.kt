package com.souvenotes.souvenotes.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_forgot_password.*

/**
 * Created on 11/21/17.
 */
class ForgotPasswordActivity : AppCompatActivity(), IForgotPasswordContract.View {

    private var forgotPresenter: IForgotPasswordContract.Presenter? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.setTitle(R.string.title_reset_password)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        forgotPresenter = ForgotPasswordPresenter(this)
        email = savedInstanceState?.getString(EXTRA_EMAIL) ?: ""

        forgot_email.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                forgotPresenter?.onEmailEntered(s.toString())
            }
        })
        forgot_email.post {
            forgot_email.setText(email)
        }

        button_reset.setOnClickListener {
            forgotPresenter?.onResetButtonClicked()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_EMAIL, email)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        forgotPresenter?.nullifyView()
    }

    override fun setEmailError(isError: Boolean, message: Int) {
        if (isError) {
            forgot_email.error = getString(message)
            forgot_email.requestFocus()
        } else {
            forgot_email.error = null
        }
    }

    override fun setResetButtonEnabled(enabled: Boolean) {
        button_reset.isEnabled = enabled
    }

    override fun hideKeyboard() {
        hideKeyboard(forgot_parent)
    }

    override fun onEmailSent() {
        Snackbar.make(forgot_parent, R.string.reset_email_sent,
            Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, { finish() }).show()
    }

    override fun onResetFailed(message: Int) {
        Snackbar.make(forgot_parent, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val EXTRA_EMAIL = "com.souvenotes.souvenotes.login.EXTRA_EMAIL"
    }
}