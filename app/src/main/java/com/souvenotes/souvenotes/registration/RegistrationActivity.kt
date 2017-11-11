package com.souvenotes.souvenotes.registration

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import com.google.firebase.auth.FirebaseAuth
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.list.NotesListActivity
import com.souvenotes.souvenotes.models.RegistrationModel
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_registration.*

/**
 * Created by NicholasCook on 10/11/17.
 */
class RegistrationActivity : AppCompatActivity(), IRegistrationContract.View {

    private val presenter = RegistrationPresenter(this)
    private var model = RegistrationModel()
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        supportActionBar?.setTitle(R.string.title_registration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()

        model = savedInstanceState?.getParcelable(EXTRA_REGISTRATION_MODEL) ?: RegistrationModel()
        presenter.setRegistrationModel(model)

        button_registration.setOnClickListener {
            presenter.onRegisterButtonClicked()
        }
        register_email.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.onEmailEntered(s.toString())
            }
        })
        register_email.setText(model.email)

        register_password.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.onPasswordEntered(s.toString())
            }
        })
        register_password.setText(model.password)

        password_confirmation.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.onPasswordConfirmationEntered(s.toString())
            }
        })
        password_confirmation.setText(model.passwordConfirmation)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(EXTRA_REGISTRATION_MODEL, model)
        super.onSaveInstanceState(outState)
    }

    override fun setRegisterButtonEnabled(enabled: Boolean) {
        button_registration.isEnabled = enabled
    }

    override fun loadNotesListActivity() {
        startActivity(Intent(this, NotesListActivity::class.java))
    }

    override fun showRegistrationError(message: Int) {
        Snackbar.make(registration_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun setEmailError(isError: Boolean, message: Int) {
        register_email.error = if (isError) getString(message) else null
    }

    override fun setPasswordError(isError: Boolean, message: Int) {
        register_password.error = if (isError) getString(message) else null
    }

    override fun setPasswordConfirmationError(isError: Boolean, message: Int) {
        password_confirmation.error = if (isError) getString(message) else null
    }

    override fun registerUser(email: String, password: String) {
        hideKeyboard(registration_parent)
        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(
                this) { result ->
            if (result.isSuccessful) {
                loadNotesListActivity()
            } else {
                Snackbar.make(registration_parent, R.string.registration_error,
                        Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val EXTRA_REGISTRATION_MODEL = "com.souvenotes.souvenotes.registration.EXTRA_REGISTRATION_MODEL"
    }
}