package com.souvenotes.souvenotes.settings.password

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
import kotlinx.android.synthetic.main.fragment_change_password.*

/**
 * Created on 11/24/17.
 */
class ChangePasswordFragment : Fragment(), IChangePasswordContract.View {

    private var passwordPresenter: IChangePasswordContract.Presenter? = null
    private var password = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        password = savedInstanceState?.getString(EXTRA_PASSWORD) ?: ""
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        change_password.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                password = s.toString()
                passwordPresenter?.onPasswordEntered(password)
            }
        })
        change_password.setText(password)

        button_submit.setOnClickListener {
            passwordPresenter?.onSubmitButtonClicked()
        }
    }

    override fun onStart() {
        super.onStart()
        passwordPresenter = ChangePasswordPresenter(this)
    }

    override fun onStop() {
        super.onStop()
        passwordPresenter?.nullifyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_PASSWORD, password)
        super.onSaveInstanceState(outState)
    }

    override fun setProgressBarVisible(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    override fun setSubmitButtonEnabled(enabled: Boolean) {
        button_submit.isEnabled = enabled
    }

    override fun setPasswordError(isError: Boolean, message: Int) {
        if (isError) {
            change_password.error = getString(message)
            change_password.requestFocus()
        } else {
            change_password.error = null
        }
    }

    override fun hideKeyboard() {
        hideKeyboard(change_password_parent)
    }

    override fun onPasswordChanged() {
        Toast.makeText(activity, R.string.change_password_success, Toast.LENGTH_LONG).show()
        activity.finish()
    }

    override fun onPasswordChangeFailed(message: Int) {
        Snackbar.make(change_password_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun logout() {
        Toast.makeText(activity, R.string.log_back_in, Toast.LENGTH_LONG).show()
        LoginActivity.logout(activity)
    }

    companion object {
        private const val EXTRA_PASSWORD = "com.souvenotes.souvenotes.settings.password"

        fun newInstance(): ChangePasswordFragment = ChangePasswordFragment()
    }
}