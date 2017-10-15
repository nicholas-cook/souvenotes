package com.souvenotes.souvenotes.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.loginoptions.LoginOptionsActivity

/**
 * Created by NicholasCook on 10/10/17.
 */
class SplashActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth?.currentUser == null) {
            finish()
            startActivity(Intent(this, LoginOptionsActivity::class.java))
        } else {
            //TODO: Load notes list
        }
    }
}