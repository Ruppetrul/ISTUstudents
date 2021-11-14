package com.chistoedet.android.istustudents.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.databinding.ProfileFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher




private val TAG = ProfileFragment::class.java.simpleName
private val TAG_PASSPORT = "passport"
private val TAG_INN = "inn"
private val TAG_SNILS = "snils"

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
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = ProfileFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getInformation().let {
                binding.passportEt.setText(it?.passport)
                binding.SNILSEt.setText(it?.snils)
                binding.INNEt.setText(it?.inn)
            }
        }

        binding.profileLinearLayout.visibility = View.VISIBLE
        binding.saveBtn.setOnClickListener {
            val saveInformation = UserInformation()
            saveInformation.passport = binding.passportEt.text.toString()
            saveInformation.inn = binding.INNEt.text.toString()
            saveInformation.snils = binding.SNILSEt.text.toString()
            checkAndSaveInfo(saveInformation)
            findNavController().navigate(R.id.action_nav_profile_to_nav_student)
        }

        val format: FormatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(PredefinedSlots.RUS_PASSPORT) // маска для серии и номера
        )
        format.installOn(binding.passportEt)

        val formatNumber: FormatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER) // маска для серии и номера
        )
        formatNumber.installOn(binding.numberEt)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val saveInformation = UserInformation()
            saveInformation.passport = binding.passportEt.text.toString()
            saveInformation.inn = binding.INNEt.text.toString()
            saveInformation.snils = binding.SNILSEt.text.toString()
            checkAndSaveInfo(saveInformation)
            findNavController().navigate(R.id.action_nav_profile_to_nav_student)
        }

    }

    private fun checkAndSaveInfo(currentUserInfo: UserInformation) {
        val newUserInfo = viewModel.getInformation()

        //TODO проверки
        if (newUserInfo?.passport != currentUserInfo.passport ||
            newUserInfo?.snils != currentUserInfo.snils ||
            newUserInfo?.inn != currentUserInfo.inn) {
            viewModel.saveInfo(currentUserInfo)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

    }

    /*private fun testPassport(passport: String) : Boolean {
        var test = false
        if (passport.isBlank()) {
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
        if (inn.isBlank()) {
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
        if (snils.isBlank()) {
            // TODO показать ошибку СНИЛС
        } else {
            if (snils.length <= 10) {
                // TODO показать ошибку короткий СНИЛС
            } else {
                test = true
            }
        }
        return test
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}