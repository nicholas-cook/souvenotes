package com.souvenotes.souvenotes.list

/**
 * Created by NicholasCook on 10/15/17.
 */
interface IListContract {

    interface View {
        fun setListVisibility(visible: Boolean)

        fun setEmptyViewVisibility(visible: Boolean)

        fun setAddButtonClickListener(atMax: Boolean)
    }

    interface Presenter {
        fun onChildChanged(itemCount: Int)
    }
}