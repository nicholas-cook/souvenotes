package com.souvenotes.souvenotes.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.settings.email.ChangeEmailFragment
import com.souvenotes.souvenotes.settings.password.ChangePasswordFragment
import com.souvenotes.souvenotes.settings.reauth.ReauthFragment

/**
 * Created on 11/23/17.
 */
class SettingsActivity : AppCompatActivity(), SettingsFragment.SettingsListener,
        ReauthFragment.ReauthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var settingsFragment = supportFragmentManager.findFragmentByTag(
                TAG_SETTINGS_FRAGMENT) as SettingsFragment?
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.settings_holder, settingsFragment,
                    TAG_SETTINGS_FRAGMENT).commit()
        }
    }

    override fun loadReauthFragment(settingsType: SettingsFragment.SettingsType) {
        var reauthFragment = supportFragmentManager.findFragmentByTag(
                TAG_REAUTH_FRAGMENT) as ReauthFragment?
        if (reauthFragment == null) {
            reauthFragment = ReauthFragment.newInstance(settingsType)
            supportFragmentManager.beginTransaction().replace(R.id.settings_holder, reauthFragment,
                    TAG_REAUTH_FRAGMENT).addToBackStack(null).commit()
        }
    }

    override fun loadEmailFragment() {
        var emailFragment = supportFragmentManager.findFragmentByTag(
                TAG_EMAIL_FRAGMENT) as ChangeEmailFragment?
        if (emailFragment == null) {
            emailFragment = ChangeEmailFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.settings_holder, emailFragment,
                    TAG_EMAIL_FRAGMENT).addToBackStack(null).commit()
        }
    }

    override fun loadPasswordFragment() {
        var passwordFragment = supportFragmentManager.findFragmentByTag(
                TAG_PASSWORD_FRAGMENT) as ChangePasswordFragment?
        if (passwordFragment == null) {
            passwordFragment = ChangePasswordFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.settings_holder,
                    passwordFragment, TAG_PASSWORD_FRAGMENT).addToBackStack(null).commit()
        }
    }

    override fun loadDeleteFragment() {
        //TODO
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val TAG_SETTINGS_FRAGMENT = "com.souvenotes.souvenotes.settings.TAG_SETTINGS_FRAGMENT"
        private const val TAG_REAUTH_FRAGMENT = "com.souvenotes.souvenotes.settings.TAG_REAUTH_FRAGMENT"
        private const val TAG_EMAIL_FRAGMENT = "com.souvenotes.souvenotes.settings.TAG_EMAIL_FRAGMENT"
        private const val TAG_PASSWORD_FRAGMENT = "com.souvenotes.souvenotes.settings.TAG_PASSWORD_FRAGMENT"
    }
}