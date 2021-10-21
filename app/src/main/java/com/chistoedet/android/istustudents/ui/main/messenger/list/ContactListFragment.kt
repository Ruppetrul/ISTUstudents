package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chistoedet.android.istustudents.databinding.FragmentContactListBinding
import com.chistoedet.android.istustudents.network.response.chats.Staffs

private val TAG = ContactListFragment::class.simpleName
class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private lateinit var viewModel: ContactListViewModel
    private var _binding: FragmentContactListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listObserver : Observer<MutableList<Staffs>?>

    private lateinit var stateObserver : Observer<ChatListState>

    private lateinit var contactListAdapter : ContactListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(ContactListViewModel::class.java)

        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.contactList.layoutManager = layoutManager

        contactListAdapter = ContactListAdapter(parentFragmentManager)
        binding.contactList.adapter = contactListAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listObserver = Observer<MutableList<Staffs>?> {

            it?.let(contactListAdapter::submitList)

            Log.d(TAG, "onCreateView: ${it.size}")
        }


        stateObserver = Observer {
            Log.i(TAG, "new state")
            when(it) {
                is ChatListState.LoadingState -> {
                    showLoading()
                    viewModel.contactList.removeObserver(listObserver)
                    Log.i(TAG, "LoadingState")
                }
                is ChatListState.ErrorState -> {
                    showError()
                    viewModel.contactList.removeObserver(listObserver)
                    Log.i(TAG, "ErrorState")
                }
                is ChatListState.ShowState -> {
                    showList()
                    viewModel.contactList.observe(viewLifecycleOwner, listObserver)
                    Log.i(TAG, "ErrorState")
                }
                is ChatListState.EmptyListState -> {
                    showEmptyList()
                    viewModel.contactList.removeObserver(listObserver)
                    Log.i(TAG, "ErrorState")
                }

                else -> {

                }
            }
        }
    }



    override fun onResume() {
        super.onResume()

        viewModel.state.observe(viewLifecycleOwner, stateObserver)


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewModel.state.removeObserver(stateObserver)
        viewModel.contactList.removeObserver(listObserver)

    }

    private fun showEmptyList() {
        binding.emptyListText.visibility = View.VISIBLE

        binding.loadingText.visibility = View.INVISIBLE
        binding.contactList.visibility = View.INVISIBLE
        binding.errorText.visibility = View.INVISIBLE
    }

    private fun showError() {
        binding.errorText.visibility = View.VISIBLE

        binding.loadingText.visibility = View.INVISIBLE
        binding.contactList.visibility = View.INVISIBLE
        binding.emptyListText.visibility = View.INVISIBLE
    }

    private fun showLoading() {
        binding.loadingText.visibility = View.VISIBLE

        binding.errorText.visibility = View.INVISIBLE
        binding.contactList.visibility = View.INVISIBLE
        binding.emptyListText.visibility = View.INVISIBLE
    }

    private fun showList() {
        binding.contactList.visibility = View.VISIBLE

        binding.errorText.visibility = View.INVISIBLE
        binding.loadingText.visibility = View.INVISIBLE
        binding.emptyListText.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}