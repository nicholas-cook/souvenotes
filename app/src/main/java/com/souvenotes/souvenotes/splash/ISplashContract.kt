package com.souvenotes.souvenotes.splash

import com.souvenotes.souvenotes.utils.IBasePresenter

/**
 * Created by NicholasCook on 10/10/17.
 */
interface ISplashContract {

    interface View {
        fun loadLoginOptionsActivity()

        fun loadNotesListActivity()
    }

    interface Presenter : IBasePresenter
}