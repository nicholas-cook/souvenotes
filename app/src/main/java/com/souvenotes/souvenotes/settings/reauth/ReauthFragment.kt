package com.souvenotes.souvenotes.settings.reauth

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.login.LoginActivity
import com.souvenotes.souvenotes.settings.SettingsFragment
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import com.souvenotes.souvenotes.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_reauth.*

/**
 * Created on 11/22/17.
 */
class ReauthFragment : Fragment(), IReauthContract.View {

    interface ReauthListener {
        fun loadEmailFragment()

        fun loadPasswordFragment()

        fun loadDeleteFragment()
    }

    private var reauthPresenter: IReauthContract.Presenter? = null
    private var settingsType: SettingsFragment.SettingsType? = null
    private var reauthListener: ReauthListener? = null
    private var password: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reauth, container, false)

        password = savedInstanceState?.getString(EXTRA_PASSWORD) ?: ""
        settingsType = savedInstanceState?.getSerializable(
            EXTRA_SETTINGS_TYPE) as SettingsFragment.SettingsType? ?: arguments.getSerializable(
            EXTRA_SETTINGS_TYPE) as SettingsFragment.SettingsType?

        when (settingsType) {
            SettingsFragment.SettingsType.EMAIL -> {
                (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.change_email)
            }
            SettingsFragment.SettingsType.PASSWORD -> {
                (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.change_password)
            }
            SettingsFragment.SettingsType.DELETE -> {
                (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.delete_account)
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ReauthListener) {
            reauthListener = context
        } else {
            throw RuntimeException("Parent activity must implement ReauthFragment.ReauthListener")
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reauth_password.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                password = s.toString()
                reauthPresenter?.onPasswordEntered(s.toString())
            }
        })
        reauth_password.setText(password)

        button_submit.setOnClickListener {
            reauthPresenter?.onSubmitButtonClicked()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_PASSWORD, password)
        outState.putSerializable(EXTRA_SETTINGS_TYPE, settingsType)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        reauthPresenter = ReauthPresenter(this)
    }

    override fun onStop() {
        super.onStop()
        reauthPresenter?.nullifyView()
    }

    override fun setProgressBarVisible(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
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
        when (settingsType) {
            SettingsFragment.SettingsType.EMAIL -> reauthListener?.loadEmailFragment()
            SettingsFragment.SettingsType.PASSWORD -> reauthListener?.loadPasswordFragment()
            SettingsFragment.SettingsType.DELETE -> reauthListener?.loadDeleteFragment()
        }
    }

    override fun onReauthFailed(message: Int) {
        Snackbar.make(reauth_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun logout() {
        Toast.makeText(activity, R.string.log_back_in, Toast.LENGTH_LONG).show()
        LoginActivity.logout(activity)
    }

    companion object {
        private const val EXTRA_PASSWORD = "com.souvenotes.souvenotes.account.EXTRA_PASSWORD"
        private const val EXTRA_SETTINGS_TYPE = "com.souvenotes.souvenotes.account.EXTRA_LOGIN_MODEL"

        fun newInstance(settingsType: SettingsFragment.SettingsType): ReauthFragment {
            val args = Bundle().apply {
                putSerializable(EXTRA_SETTINGS_TYPE, settingsType)
            }
            return ReauthFragment().apply {
                arguments = args
            }
        }
    }
}