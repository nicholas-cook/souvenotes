package com.souvenotes.souvenotes.loginoptions

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.souvenotes.souvenotes.LoginActivity
import com.souvenotes.souvenotes.R

/**
 * Created by NicholasCook on 10/10/17.
 */
class LoginOptionsActivity : AppCompatActivity(), ILoginOptionsContract.View {

    private val optionsPresenter = LoginOptionsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_options)


    }

    override fun loadLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun loadRegistrationActivity() {
        //TODO: Load registration activity
    }
}