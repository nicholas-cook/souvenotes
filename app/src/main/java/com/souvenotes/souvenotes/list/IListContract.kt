package com.souvenotes.souvenotes.list

import android.support.annotation.StringRes
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.utils.IBasePresenter

/**
 * Created by NicholasCook on 10/15/17.
 */
interface IListContract {

    interface View {
        fun onNotesAvailable(notes: List<NoteModel>)

        fun onLoadNotesError(@StringRes message: Int)
    }

    interface Presenter : IBasePresenter {
        fun refreshList()
    }
}