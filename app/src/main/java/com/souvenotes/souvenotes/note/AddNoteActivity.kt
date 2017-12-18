package com.souvenotes.souvenotes.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.souvenotes.souvenotes.BuildConfig
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.login.LoginActivity
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_add_note.*

/**
 * Created on 10/23/17.
 */
class AddNoteActivity : AppCompatActivity(), IAddNotesContract.View {

    private var addNotePresenter: IAddNotesContract.Presenter? = null
    private val noteModel = NoteModel()

    private var noteKey: String? = null
    private var isDeleting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteKey = savedInstanceState?.getString(EXTRA_NOTE_KEY) ?: intent?.getStringExtra(
            EXTRA_NOTE_KEY)

        setTitle(if (noteKey != null) R.string.title_edit_note else R.string.title_add_note)

        note_title.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                noteModel.title = s.toString()
            }
        })
        note_content.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                noteModel.content = s.toString()
            }
        })
        if (BuildConfig.DEBUG) {
            ad_view.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    ad_view.visibility = View.VISIBLE
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    ad_view.visibility = View.GONE
                }
            }
            ad_view.loadAd(AdRequest.Builder().build())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_note -> {
                showNoteDeletionConfirmation()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showNoteDeletionConfirmation() {
        val builder = AlertDialog.Builder(this).apply {
            setMessage(R.string.message_delete)
            setPositiveButton(R.string.option_delete
            ) { dialog, _ ->
                dialog.dismiss()
                addNotePresenter?.deleteNote()
            }
            setNegativeButton(android.R.string.cancel
            ) { dialog, _ -> dialog.dismiss() }
        }
        builder.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_NOTE_KEY, noteKey)
        super.onSaveInstanceState(outState)
    }

    override fun onAddNoteError(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onNoteLoaded(note: NoteModel) {
        //Use append to move cursor to end of text
        note_title.append(note.title)
        note_content.setText(note.content)
    }

    override fun onLoadNoteError() {
        Toast.makeText(this, R.string.note_load_error, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun logout() {
        LoginActivity.logout(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onNoteDeleteError() {
        isDeleting = false
        Snackbar.make(add_note_parent, R.string.delete_error, Snackbar.LENGTH_LONG).show()
    }

    override fun onNoteDeleted() {
        isDeleting = true
        finish()
    }

    override fun onStart() {
        super.onStart()
        addNotePresenter = AddNotePresenter(this, noteKey)
        addNotePresenter?.start()
    }

    override fun onStop() {
        if (!isDeleting) {
            addNotePresenter?.saveNote(note_title.text.toString(), note_content.text.toString())
        }
        addNotePresenter?.stop()
        super.onStop()
    }

    companion object {
        private const val EXTRA_NOTE_KEY = "com.souvenotes.souvenotes.note"

        fun editNote(context: Context, noteKey: String) {
            val edit = Intent(context, AddNoteActivity::class.java).apply {
                putExtra(EXTRA_NOTE_KEY, noteKey)
            }
            context.startActivity(edit)
        }
    }
}