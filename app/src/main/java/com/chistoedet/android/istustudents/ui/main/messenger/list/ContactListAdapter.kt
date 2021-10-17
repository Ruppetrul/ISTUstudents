package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.network.response.chats.Staffs

private val TAG = ContactListAdapter::class.simpleName
class ContactListAdapter :
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


        init {
            itemView.setOnClickListener(this)
            Log.i(TAG, "init mess holder")
            nameTextView = itemView.findViewById(R.id.contact_name)
            latestMessage = itemView.findViewById(R.id.latest_message)
        }

        //val bindDrawable: (Drawable) -> Unit = itemImageView::setImageDrawable

        fun bindMessage(staffs: Staffs) {

            nameTextView.text = staffs.staff?.getFamily()

            staffs.latestMessage?.apply {
                latestMessage.text = this.message

                if (this.isRead == false) {
                    latestMessage.setBackgroundColor(Color.GRAY)
                }

            }

            //nameTextView.text = mess
        }

        override fun onClick(v: View?) {


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