package com.souvenotes.souvenotes.note

import android.support.annotation.StringRes

/**
 * Created by NicholasCook on 10/23/17.
 */
interface IAddNotesContract {

    interface Presenter {
        fun saveNote(title: String, content: String)
    }

    interface View {
        fun onAddNoteError(@StringRes message: Int)

        fun onNoteAdded()
    }
}