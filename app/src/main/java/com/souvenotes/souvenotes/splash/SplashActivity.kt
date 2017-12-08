package com.souvenotes.souvenotes.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.souvenotes.souvenotes.list.NotesListActivity
import com.souvenotes.souvenotes.login.LoginActivity

/**
 * Created by NicholasCook on 10/10/17.
 */
class SplashActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth?.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, NotesListActivity::class.java))
        }
        finish()
    }
}