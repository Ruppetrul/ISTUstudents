package com.chistoedet.android.istustudents.ui.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chistoedet.android.istustudents.databinding.NewsFragmentBinding
import com.vk.api.sdk.VK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
    private val TAG = NewsFragment::class.simpleName

    private var _binding: NewsFragmentBinding? = null

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    private lateinit var contactListAdapter : NewsListAdapter

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

        VK.isLoggedIn().apply {
            if (this) {

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.news.collectLatest {
                        contactListAdapter.submitData(it)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}