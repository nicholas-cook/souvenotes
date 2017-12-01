package com.souvenotes.souvenotes.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.list.NotesListActivity
import com.souvenotes.souvenotes.login.forgotpassword.ForgotPasswordActivity
import com.souvenotes.souvenotes.models.LoginModel
import com.souvenotes.souvenotes.registration.RegistrationActivity
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by NicholasCook on 10/10/17.
 */
class LoginActivity : AppCompatActivity(), ILoginContract.View {

    private var loginPresenter: ILoginContract.Presenter? = null
    private var loginModel = LoginModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setTitle(R.string.title_login)

        loginPresenter = LoginPresenter(this)

        loginModel = savedInstanceState?.getParcelable(EXTRA_LOGIN_MODEL) ?: LoginModel()

        login_email.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                loginPresenter?.onEmailEntered(s.toString())
            }
        })
        login_email.setText(loginModel.email)

        login_password.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                loginPresenter?.onPasswordEntered(s.toString())
            }
        })
        login_password.setText(loginModel.password)

        button_sign_up.setOnClickListener {
            loginPresenter?.onLoginButtonClicked()
        }

        create_account.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(EXTRA_LOGIN_MODEL, loginModel)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        loginPresenter?.nullifyView()
    }

    override fun setProgressBarVisible(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    override fun setEmailError(isError: Boolean, message: Int) {
        if (isError) {
            login_email.error = getString(message)
            login_email.requestFocus()
        } else {
            login_email.error = null
        }
    }

    override fun setPasswordError(isError: Boolean, message: Int) {
        if (isError) {
            login_password.error = getString(message)
            login_password.requestFocus()
        } else {
            login_password.error = null
        }
    }

    private fun loadNotesListActivity() {
        val toNotesList = Intent(this, NotesListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(toNotesList)
    }

    override fun onLoginSuccess() {
        loadNotesListActivity()
    }

    override fun onLoginFailed(message: Int) {
        Snackbar.make(login_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun hideKeyboard() {
        hideKeyboard(login_parent)
    }

    override fun setLoginButtonEnabled(enabled: Boolean) {
        button_sign_up.isEnabled = enabled
    }

    companion object {
        private const val EXTRA_LOGIN_MODEL = "com.souvenotes.souvenotes.login.EXTRA_LOGIN_MODEL"

        fun logout(context: Context) {
            FirebaseAuth.getInstance().signOut()
            val logout = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(logout)
        }
    }
}