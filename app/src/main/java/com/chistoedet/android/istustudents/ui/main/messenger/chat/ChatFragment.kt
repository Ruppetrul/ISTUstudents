package com.chistoedet.android.istustudents.ui.main.messenger.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.ChatFragmentBinding
import com.chistoedet.android.istustudents.databinding.ItemMessageReceiveBinding
import com.chistoedet.android.istustudents.databinding.ItemMessageSendBinding
import com.chistoedet.android.istustudents.network.response.chats.Message
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val TAG = ChatFragment::class.java.simpleName
class ChatFragment(var user: Staffs) : Fragment() {

    companion object {
        fun newInstance(user : Staffs) = ChatFragment(user)
    }

    private lateinit var viewModel: ChatViewModel
    private lateinit var stateObserver : Observer<ChatState>

    private var _binding: ChatFragmentBinding? = null

    private val binding get() = _binding!!

    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.recyclerView.adapter = messageAdapter
        //receiveAutoResponse()

        (activity as AppCompatActivity?)!!.supportActionBar?.title = user.staff?.getFamily() + " " + user.staff?.getName()?.get(0) + "." + user.staff?.getPatronymic()?.get(0) + "."
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.updateChatHistory(user.id!!)

        viewModel.chatHistory.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "пришло на популяцию ${it.size}")
            populateData(it)
            messageAdapter.notifyDataSetChanged()
        })

    }

    private fun populateData(list: List<Message>) {
        list.forEach {
            if (it.from?.id == user.id) {
                messageAdapter.add(ReceiveMessageItem(it))
            } else {
                messageAdapter.add(SendMessageItem(it))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.state.removeObserver(stateObserver)

    }
    private fun receiveAutoResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            val receive = Message()
            receive.message = "Привет с того света"
            receive.from?.id = user.id
            val receiveItem = ReceiveMessageItem(receive)

            messageAdapter.add(receiveItem)
        }
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
       /* binding.loadingText.visibility = View.INVISIBLE
        binding.errorText.visibility = View.VISIBLE
        binding.chatText.visibility = View.INVISIBLE*/
       /* message.visibility = View.INVISIBLE
        messages.visibility = View.INVISIBLE
        errorImage.visibility = View.VISIBLE
        errorText.visibility = View.VISIBLE
        tryAgainButton.visibility = View.VISIBLE
        loadingImage.visibility = View.INVISIBLE*/
    }

    private fun showChat() {
        Log.i(TAG,"showChat")
   /*     binding.loadingText.visibility = View.INVISIBLE
        binding.errorText.visibility = View.INVISIBLE
        binding.chatText.visibility = View.VISIBLE*/

       /* message.visibility = View.VISIBLE
        messages.visibility = View.VISIBLE
        errorImage.visibility = View.INVISIBLE
        errorText.visibility = View.INVISIBLE
        tryAgainButton.visibility = View.INVISIBLE
        loadingImage.visibility = View.INVISIBLE*/
    }

    private fun showLoading() {
        Log.i(TAG,"showLoading")
      /*  binding.loadingText.visibility = View.VISIBLE
        binding.errorText.visibility = View.INVISIBLE
        binding.chatText.visibility = View.INVISIBLE*/
        /*message.visibility = View.INVISIBLE
        messages.visibility = View.INVISIBLE
        errorImage.visibility = View.INVISIBLE
        errorText.visibility = View.INVISIBLE
        tryAgainButton.visibility = View.INVISIBLE
        loadingImage.visibility = View.VISIBLE*/
    }
}

class SendMessageItem(private val message: Message) : BindableItem<ItemMessageSendBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_send
    }

    override fun bind(viewBinding: ItemMessageSendBinding, position: Int) {
        viewBinding.message = message
    }
}

class ReceiveMessageItem(private val message: Message) : BindableItem<ItemMessageReceiveBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_receive
    }

    override fun bind(viewBinding: ItemMessageReceiveBinding, position: Int) {
        viewBinding.message = message
    }
}