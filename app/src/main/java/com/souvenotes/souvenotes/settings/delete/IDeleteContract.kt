package com.souvenotes.souvenotes.settings.delete

import android.support.annotation.StringRes

/**
 * Created on 11/25/17.
 */
interface IDeleteContract {

    interface View {
        fun onAccountDeleted()

        fun onDeletionError(@StringRes message: Int)

        fun logout()
    }

    interface Presenter {
        fun onDeletionConfirmed()

        fun nullifyView()
    }
}