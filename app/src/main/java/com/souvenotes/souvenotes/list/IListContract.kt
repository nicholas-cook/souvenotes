package com.souvenotes.souvenotes.list

import com.google.firebase.database.DatabaseReference

/**
 * Created by NicholasCook on 10/15/17.
 */
interface IListContract {

    interface View {
        fun setListVisibility(visible: Boolean)

        fun setEmptyViewVisibility(visible: Boolean)

        fun setAddButtonClickListener(atMax: Boolean)

        fun showNoteDeletionError()

        fun logout()
    }

    interface Presenter {
        fun onChildChanged(itemCount: Int)

        fun deleteNote(listRef: DatabaseReference, noteKey: String)
    }
}