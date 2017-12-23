package com.souvenotes.souvenotes.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.login.LoginActivity
import com.souvenotes.souvenotes.models.NoteListModel
import com.souvenotes.souvenotes.note.AddNoteActivity
import com.souvenotes.souvenotes.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_notes_list.*

/**
 * Created on 10/15/17.
 */
class NotesListActivity : AppCompatActivity(), IListContract.View {

    private var listPresenter: IListContract.Presenter? = null
    private var notesAdapter: NotesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        notesAdapter = NotesListAdapter(this, getListOptions())
        notes_recycler_view.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        notes_recycler_view.adapter = notesAdapter
        notes_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    add_note.hide()
                } else if (dy < 0) {
                    add_note.show()
                }
            }
        })
        add_note.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notes_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                showLogoutConfirmation()
                return true
            }
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLogoutConfirmation() {
        val builder = AlertDialog.Builder(this).apply {
            setMessage(R.string.confirm_logout)
            setPositiveButton(R.string.action_logout, { dialog, _ ->
                dialog.dismiss()
                notesAdapter?.stopListening()
                LoginActivity.logout(this@NotesListActivity)
            })
            setNegativeButton(android.R.string.cancel, { dialog, _ ->
                dialog.dismiss()
            })
        }
        builder.show()
    }

    override fun onStart() {
        super.onStart()
        listPresenter = NotesListPresenter(this)
        notesAdapter?.startListening()
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

    override fun onStop() {
        notesAdapter?.stopListening()
        super.onStop()
    }

    fun showNotesError() {
        Snackbar.make(list_parent, R.string.load_notes_error, Snackbar.LENGTH_LONG).show()
    }

    fun showNoteDeletionConfirmation(noteKey: String) {
        val builder = AlertDialog.Builder(this).apply {
            setMessage(R.string.message_delete)
            setPositiveButton(R.string.option_delete
            ) { dialog, _ ->
                dialog.dismiss()
                listPresenter?.deleteNote(noteKey)
                notesAdapter?.resetSelectedPosition()
            }
            setNegativeButton(android.R.string.cancel
            ) { dialog, _ ->
                dialog.dismiss()
                notesAdapter?.resetSelectedPosition()
            }
            setOnCancelListener { dialog ->
                dialog.dismiss()
                notesAdapter?.resetSelectedPosition()
            }
        }
        builder.show()
    }

    override fun showNoteDeletionError() {
        Snackbar.make(list_parent, R.string.delete_error, Snackbar.LENGTH_LONG).show()
    }

    fun onChildChanged(itemCount: Int) {
        if (!add_note.isShown) {
            add_note.show()
        }
        listPresenter?.onChildChanged(itemCount)
    }

    override fun logout() {
        LoginActivity.logout(this)
    }

    override fun setListVisibility(visible: Boolean) {
        notes_recycler_view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        empty_view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun setAddButtonClickListener(atMax: Boolean) {
        if (atMax) {
            add_note.setOnClickListener {
                Snackbar.make(list_parent, R.string.max_notes, Snackbar.LENGTH_LONG).show()
            }
        } else {
            add_note.setOnClickListener {
                startActivity(Intent(this, AddNoteActivity::class.java))
            }
        }
    }

    private fun getListOptions(): FirebaseRecyclerOptions<NoteListModel> {
        val databaseRef = FirebaseDatabase.getInstance().reference
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            logout()
        }
        return FirebaseRecyclerOptions.Builder<NoteListModel>()
            .setQuery(databaseRef.child("notes-list").child(userId).orderByChild(
                "timestamp").limitToLast(110), NoteListModel::class.java)
            .build()
    }
}