package com.chistoedet.android.istustudents.ui.main.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chistoedet.android.istustudents.MainActivity
import com.chistoedet.android.istustudents.databinding.NewsFragmentBinding
import com.vk.api.sdk.VK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NewsFragment : Fragment(), MainActivity.Callbacks {
    private val TAG = NewsFragment::class.simpleName

    private var _binding: NewsFragmentBinding? = null

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    private lateinit var contactListAdapter : NewsListAdapter

    private lateinit var stateObserver : Observer<VKState>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = NewsFragmentBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.newsList.layoutManager = layoutManager

        contactListAdapter = NewsListAdapter()
        binding.newsList.adapter = contactListAdapter

        stateObserver = Observer {
            when(it) {
                is VKState.LoginState -> {
                    showNews()
                }
                is VKState.NotLoginState -> {
                    showError()
                }

                else -> {

                }
            }
        }

        return binding.root
    }

    private fun updateNews() {
        Log.d(TAG, "updateNews: ${VK.isLoggedIn()}")
        VK.isLoggedIn().apply {
            if (this) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.news.collectLatest {
                        contactListAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun showError() {
        binding.newsList.visibility = View.INVISIBLE
        binding.vkErrorText.visibility = View.VISIBLE
    }

    private fun showNews() {
        binding.newsList.visibility = View.VISIBLE
        binding.vkErrorText.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        if (VK.isLoggedIn()) viewModel.state.postValue(VKState.LoginState())
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        updateNews()
    }

    override fun onStop() {
        super.onStop()
        viewModel.state.removeObserver(stateObserver)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onNewsUpdated() {
        Log.d(TAG, "onNewsUpdated")

    }

}