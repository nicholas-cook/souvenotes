package com.souvenotes.souvenotes.list

/**
 * Created by NicholasCook on 10/15/17.
 */
class NotesListPresenter(private val listView: IListContract.View?) : IListContract.Presenter {

    override fun onChildChanged(itemCount: Int) {
        listView?.setEmptyViewVisibility(itemCount <= 0)
        listView?.setListVisibility(itemCount > 0)
        listView?.setAddButtonClickListener(itemCount >= 100)
    }
}