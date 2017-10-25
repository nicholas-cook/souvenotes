package com.souvenotes.souvenotes.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.note.AddNoteActivity
import kotlinx.android.synthetic.main.activity_notes_list.*

/**
 * Created by NicholasCook on 10/15/17.
 */
class NotesListActivity : AppCompatActivity(), IListContract.View {

    private val listPresenter = NotesListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        listPresenter.start()
        setTitle(R.string.title_notes)

        add_note.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    override fun onNotesAvailable(notes: List<NoteModel>) {
        empty_view.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onLoadNotesError(message: Int) {
        empty_view.visibility = View.VISIBLE
        Snackbar.make(list_parent, message, Snackbar.LENGTH_LONG).show()
    }
}