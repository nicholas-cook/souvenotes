package com.souvenotes.souvenotes.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import com.google.firebase.auth.FirebaseAuth
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.list.NotesListActivity
import com.souvenotes.souvenotes.models.LoginModel
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by NicholasCook on 10/10/17.
 */
class LoginActivity : AppCompatActivity(), ILoginContract.View {

    private val loginPresenter = LoginPresenter(this)
    private var loginModel = LoginModel()
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        loginModel = savedInstanceState?.getParcelable(EXTRA_LOGIN_MODEL) ?: LoginModel()

        login_email.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                loginPresenter.onEmailEntered(s.toString())
            }
        })
        login_email.setText(loginModel.email)

        login_password.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                loginPresenter.onPasswordEntered(s.toString())
            }
        })
        login_password.setText(loginModel.password)

        button_sign_up.setOnClickListener {
            loginPresenter.onLoginButtonClicked()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(EXTRA_LOGIN_MODEL, loginModel)
        super.onSaveInstanceState(outState)
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

    override fun logInUser(email: String, password: String) {
        hideKeyboard(login_parent)
        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(
                this) { result ->
            if (result.isSuccessful) {
                loadNotesListActivity()
            } else {
                Snackbar.make(login_parent, R.string.login_error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun setLoginButtonEnabled(enabled: Boolean) {
        button_sign_up.isEnabled = enabled
    }

    companion object {
        private const val EXTRA_LOGIN_MODEL = "com.souvenotes.souvenotes.login.EXTRA_LOGIN_MODEL"

        fun logout(context: Context) {
            val logout = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(logout)
        }
    }
}