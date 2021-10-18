package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.network.response.chats.LatestMessage
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import com.chistoedet.android.istustudents.ui.main.messenger.chat.ChatFragment
import com.chistoedet.android.istustudents.ui.main.profile.ProfileFragment

private val TAG = ContactListAdapter::class.simpleName
class ContactListAdapter(var fragmentManager: FragmentManager) :
    ListAdapter<Staffs, ContactListAdapter.ContactHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(view)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val staffs = getItem(position)
        Log.i(TAG, "bind message $position")
        holder.bindMessage(staffs)
    }

    inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var nameTextView: TextView
        private var latestMessage: TextView
        private lateinit var _staffs: Staffs

        init {
            itemView.setOnClickListener(this)
            nameTextView = itemView.findViewById(R.id.contact_name)
            latestMessage = itemView.findViewById(R.id.latest_message)
        }

        //val bindDrawable: (Drawable) -> Unit = itemImageView::setImageDrawable

        fun bindMessage(staffs: Staffs) {
            _staffs = staffs
            nameTextView.text = staffs.staff!!.getFamily()

            val latestMess = staffs.latestMessage
            staffs.latestMessage.apply {

                latestMessage.text = this?.message

            }

            //nameTextView.text = mess
        }

        override fun onClick(v: View?) {

            fragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, ChatFragment.newInstance(_staffs))
                //.detach(oldFragment)
                .addToBackStack(null)
                .commit()

        }
    }



    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Staffs> =
            object : DiffUtil.ItemCallback<Staffs>() {


                override fun areItemsTheSame(oldItem: Staffs, newItem: Staffs): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: Staffs, newItem: Staffs): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }


}