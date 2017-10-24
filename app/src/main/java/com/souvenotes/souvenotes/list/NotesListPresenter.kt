package com.souvenotes.souvenotes.list

/**
 * Created by NicholasCook on 10/15/17.
 */
class NotesListPresenter(private val listView: IListContract.View?) : IListContract.Presenter {

    override fun start() {
        listView?.onNotesAvailable(listOf())
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}