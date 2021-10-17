package com.chistoedet.android.istustudents.ui.main.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.databinding.FragmentGalleryBinding

class PortfolioFragment : Fragment() {

    private lateinit var portfolioViewModel: PortfolioViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        portfolioViewModel =
            ViewModelProvider(this).get(PortfolioViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        /*val textView: TextView = binding.textGallery
        portfolioViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}