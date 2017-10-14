package com.souvenotes.souvenotes.loginoptions

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.souvenotes.souvenotes.LoginActivity
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_login_options.*

/**
 * Created by NicholasCook on 10/10/17.
 */
class LoginOptionsActivity : AppCompatActivity(), ILoginOptionsContract.View {

    private val optionsPresenter = LoginOptionsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_options)
        setTitle(R.string.title_login_options)

        option_login.setOnClickListener {
            optionsPresenter.onLoginClicked()
        }
        option_register.setOnClickListener {
            optionsPresenter.onRegisterClicked()
        }
    }

    override fun loadLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun loadRegistrationActivity() {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}