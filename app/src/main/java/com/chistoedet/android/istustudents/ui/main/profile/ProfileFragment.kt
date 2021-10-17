package com.chistoedet.android.istustudents.ui.main.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.databinding.ProfileFragmentBinding

private val TAG = ProfileFragment::class.java.simpleName
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var _binding: ProfileFragmentBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    private lateinit var infoObserver : Observer<UserInformation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.userInformation.observe(viewLifecycleOwner, infoObserver)

        binding.saveBtn.setOnClickListener {
            val saveInformation = UserInformation()
            saveInformation.passport = binding.passportEt.text.toString()
            saveInformation.inn = binding.INNEt.text.toString()
            saveInformation.snils = binding.SNILSEt.text.toString()

            viewModel.saveInfo(saveInformation)

            activity?.onBackPressed()
        }
    }

    private fun testPassport(passport: String) : Boolean {
        var test = false
        if (passport.isNullOrEmpty()) {
            // TODO показать ошибку паспорт
        } else {
            if (passport.length <= 10) {
                // TODO показать ошибку короткий паспорт
                } else {
                test = true
            }
        }
        return test
    }

    private fun testInn(inn: String) : Boolean {
        var test = false
        if (inn.isNullOrEmpty()) {
            // TODO показать ошибку ИНН
        } else {
            if (inn.length <= 10) {
                // TODO показать ошибку короткий ИНН
            } else {
                test = true
            }
        }
        return test
    }

    private fun testSnils(snils: String) : Boolean {
        var test = false
        if (snils.isNullOrEmpty()) {
            // TODO показать ошибку СНИЛС
        } else {
            if (snils.length <= 10) {
                // TODO показать ошибку короткий СНИЛС
            } else {
                test = true
            }
        }
        return test
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

    }

    override fun onStop() {
        super.onStop()
        viewModel.userInformation.removeObserver(infoObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoObserver = Observer {
            binding.passportEt.setText(it.passport)
            binding.SNILSEt.setText(it.snils)
            binding.INNEt.setText(it.inn)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}