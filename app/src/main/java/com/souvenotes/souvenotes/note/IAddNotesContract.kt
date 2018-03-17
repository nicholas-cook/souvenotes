package com.souvenotes.souvenotes.note

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.utils.IBasePresenter

/**
 * Created on 10/23/17.
 */
interface IAddNotesContract {

    interface View {
        fun onAddNoteError(@StringRes message: Int)

        fun onNoteLoaded(note: NoteModel)

        fun onLoadNoteError()

        fun onNoteDeleteError()

        fun onNoteDeleted()

        fun logout()
    }

    interface Presenter : IBasePresenter {
        fun saveNote(title: String, content: String): String?

        fun deleteNote()
    }
}