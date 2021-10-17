package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chistoedet.android.istustudents.databinding.FragmentContactListBinding
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment
import kotlin.math.log

private val TAG = ContactListFragment::class.simpleName
class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private lateinit var messengerViewModel: MessengerViewModel
    private var _binding: FragmentContactListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listObserver : Observer<MutableList<Staffs>?>

    lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        messengerViewModel =
            ViewModelProvider(this).get(MessengerViewModel::class.java)

        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        recyclerView = binding.contactList

        var layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        var chatAdapter = ContactListAdapter()
        recyclerView.adapter = chatAdapter

        listObserver = Observer<MutableList<Staffs>?> {
            chatAdapter.apply {
                submitList(it)
            }
            Log.d(TAG, "onCreateView: ${it.size}")
        }
        messengerViewModel.contactList.observe(viewLifecycleOwner, listObserver)

        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}