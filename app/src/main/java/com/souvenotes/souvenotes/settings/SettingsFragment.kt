package com.souvenotes.souvenotes.settings

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.souvenotes.souvenotes.R
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.settings_item.view.*

/**
 * Created on 11/23/17.
 */
class SettingsFragment : Fragment() {

    interface SettingsListener {
        fun loadReauthFragment(settingsType: SettingsType)
    }

    private var settingsListener: SettingsListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_settings,
            container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.title_settings)

        val settingsOptions = resources.getStringArray(R.array.settings_options)

        settings_recycler_view.addItemDecoration(
                DividerItemDecoration(activity, LinearLayout.VERTICAL))
        settings_recycler_view.adapter = SettingsAdapter(settingsOptions)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SettingsListener) {
            settingsListener = context
        } else {
            throw RuntimeException(
                    "Parent activity must implement SettingsFragment.SettingsListener")
        }
    }

    inner class SettingsAdapter(private val settingsOptions: Array<String>) :
            RecyclerView.Adapter<SettingsViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.settings_item, parent,
                    false)
            return SettingsViewHolder(view)
        }

        override fun getItemCount(): Int = settingsOptions.size

        override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
            holder.itemView.settings_title.text = settingsOptions[position]
            holder.itemView.setOnClickListener {
                settingsListener?.loadReauthFragment(SettingsType.values()[holder.adapterPosition])
            }
        }

    }

    inner class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    enum class SettingsType {
        EMAIL,
        PASSWORD,
        DELETE
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}