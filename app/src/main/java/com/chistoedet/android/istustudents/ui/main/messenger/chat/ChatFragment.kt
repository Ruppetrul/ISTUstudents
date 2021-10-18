package com.chistoedet.android.istustudents.ui.main.messenger.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.chistoedet.android.istustudents.databinding.ChatFragmentBinding
import com.chistoedet.android.istustudents.network.response.chats.Staffs

private val TAG = ChatFragment::class.java.simpleName
class ChatFragment(var user: Staffs) : Fragment() {

    companion object {
        fun newInstance(user : Staffs) = ChatFragment(user)
    }

    private lateinit var viewModel: ChatViewModel
    private lateinit var stateObserver : Observer<ChatState>

    private var _binding: ChatFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.updateChatHistory(user.id!!)

        viewModel.chatHistory.observe(viewLifecycleOwner, Observer {
            it.forEach{
                binding.chatText.text = "${it.from?.name}: ${it.message}" + System.lineSeparator()
            }
        })

    }

    override fun onStop() {
        super.onStop()
        viewModel.state.removeObserver(stateObserver)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        Log.d(TAG, "onActivityCreated: $user")

        stateObserver = Observer {
            Log.i(TAG, "new state")
            when(it) {
                is ChatState.LoadingState -> {
                    showLoading()
                    Log.i(TAG, "LoadingState")
                }
                is ChatState.ErrorState<*> -> {
                    showError()
                    //chatViewModel.myChatLiveData.removeObserver(chatObserver)
                    Log.i(TAG, "ErrorState")
                }
                is ChatState.NoMessageState -> {
                    showChat()
                    Log.i(TAG, "NoMessageState")
                }
                is ChatState.ActiveChatState -> {
                    showChat()
                    //chatViewModel.myChatLiveData.observe(viewLifecycleOwner, chatObserver)
                    Log.i(TAG, "ShowChatState")
                }
                else -> {

                }
            }
        }
        // TODO: Use the ViewModel
    }


    private fun showError() {
        Log.i(TAG,"showError")
        binding.loadingText.visibility = View.INVISIBLE
        binding.errorText.visibility = View.VISIBLE
        binding.chatText.visibility = View.INVISIBLE
       /* message.visibility = View.INVISIBLE
        messages.visibility = View.INVISIBLE
        errorImage.visibility = View.VISIBLE
        errorText.visibility = View.VISIBLE
        tryAgainButton.visibility = View.VISIBLE
        loadingImage.visibility = View.INVISIBLE*/
    }

    private fun showChat() {
        Log.i(TAG,"showChat")
        binding.loadingText.visibility = View.INVISIBLE
        binding.errorText.visibility = View.INVISIBLE
        binding.chatText.visibility = View.VISIBLE

       /* message.visibility = View.VISIBLE
        messages.visibility = View.VISIBLE
        errorImage.visibility = View.INVISIBLE
        errorText.visibility = View.INVISIBLE
        tryAgainButton.visibility = View.INVISIBLE
        loadingImage.visibility = View.INVISIBLE*/
    }

    private fun showLoading() {
        Log.i(TAG,"showLoading")
        binding.loadingText.visibility = View.VISIBLE
        binding.errorText.visibility = View.INVISIBLE
        binding.chatText.visibility = View.INVISIBLE
        /*message.visibility = View.INVISIBLE
        messages.visibility = View.INVISIBLE
        errorImage.visibility = View.INVISIBLE
        errorText.visibility = View.INVISIBLE
        tryAgainButton.visibility = View.INVISIBLE
        loadingImage.visibility = View.VISIBLE*/
    }
}