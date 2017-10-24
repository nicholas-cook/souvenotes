package com.souvenotes.souvenotes.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.utils.DateTimeUtils
import kotlinx.android.synthetic.main.notes_list_item.view.*

/**
 * Created by NicholasCook on 10/15/17.
 */
class NotesListAdapter(options: FirebaseRecyclerOptions<NoteModel>) :
        FirebaseRecyclerAdapter<NoteModel, NotesListAdapter.NotesViewHolder>(options) {

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int, model: NoteModel) {
        holder.itemView.note_title.text = model.title
        holder.itemView.note_date.text = DateTimeUtils.getDisplayFormat(model.timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(inflater.inflate(R.layout.notes_list_item, parent, false))
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}