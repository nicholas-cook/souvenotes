package com.souvenotes.souvenotes.splash

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by NicholasCook on 10/10/17.
 */
class SplashPresenter(private val splashView: ISplashContract.View?) : ISplashContract.Presenter {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun start() {
        val user = firebaseAuth.currentUser
        if (user == null) {
            splashView?.loadLoginOptionsActivity()
        } else {
            splashView?.loadNotesListActivity()
        }
    }

    override fun stop() {
    }
}