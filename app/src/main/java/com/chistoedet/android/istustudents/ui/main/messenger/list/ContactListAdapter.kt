package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chistoedet.android.istustudents.BR
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.ContactItemBinding
import com.chistoedet.android.istustudents.network.response.chats.Staffs

private val TAG = ContactListAdapter::class.simpleName
class ContactListAdapter() :
    ListAdapter<Staffs, ContactListAdapter.ContactHolder>(DIFF_CALLBACK) {

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onChatSelected(user: Staffs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ContactItemBinding>(inflater, viewType, parent, false)
        return ContactHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) = holder.bindMessage(getItem(position))

    inner class ContactHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var nameTextView: TextView
        private var latestMessage: TextView
        private lateinit var _staffs: Staffs

        init {
            itemView.setOnClickListener(this)
            nameTextView = itemView.findViewById(R.id.contact_name)
            latestMessage = itemView.findViewById(R.id.latest_message)
        }

        fun bindMessage(staffs: Staffs) {
            binding.setVariable(BR.staffs, staffs)
            _staffs = staffs
        }

        override fun onClick(v: View?) {
            var bundle = Bundle()
            bundle.putSerializable("user", _staffs)
            v?.findNavController()?.navigate(R.id.action_nav_contact_list_to_nav_chat, bundle)
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


    override fun getItemViewType(position: Int): Int = R.layout.contact_item

}