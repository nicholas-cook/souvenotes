package com.souvenotes.souvenotes.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.loginoptions.LoginOptionsActivity

/**
 * Created by NicholasCook on 10/10/17.
 */
class SplashActivity : AppCompatActivity(), ISplashContract.View {

    private val splashPresenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        splashPresenter.start()
    }

    override fun loadLoginOptionsActivity() {
        startActivity(Intent(this, LoginOptionsActivity::class.java))
    }

    override fun loadNotesListActivity() {

    }

}