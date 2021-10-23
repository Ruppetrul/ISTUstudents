package com.chistoedet.android.istustudents.ui.main.messenger.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.ChatFragmentBinding
import com.chistoedet.android.istustudents.databinding.ItemMessageReceiveBinding
import com.chistoedet.android.istustudents.databinding.ItemMessageSendBinding
import com.chistoedet.android.istustudents.network.response.chats.From
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
class ChatFragment() : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel
    private lateinit var stateObserver : Observer<ChatState>
    private lateinit var chatHistoryObserver : Observer<MutableList<Message>>

    private var _binding: ChatFragmentBinding? = null

    private val binding get() = _binding!!
    private var user : Staffs ?= null
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        _binding = ChatFragmentBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            user = arguments?.getSerializable("user") as Staffs?
            user?.id?.let { viewModel.updateChatHistory(it) }
        }
        binding.recyclerView.adapter = messageAdapter

        //(activity as AppCompatActivity?)!!.supportActionBar?.title = user?.staff?.getFamily() + " " + user?.staff?.getName()?.get(0) + "." + user?.staff?.getPatronymic()?.get(0) + "."

        viewModel.chatHistory.observe(viewLifecycleOwner, chatHistoryObserver)

        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("user", user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

        chatHistoryObserver = Observer {
            Log.d(TAG, "пришло на популяцию ${it.size}")
            populateData(it)
        }

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
                    showEmptyChat()
                    Log.i(TAG, "NoMessageState")
                }
                is ChatState.ActiveChatState -> {
                    showChat()
                    //chatViewModel.myChatLiveData.observe(viewLifecycleOwner, chatObserver)
                    Log.i(TAG, "ShowChatState")
                }
                is ChatState.ConnectionError -> {
                    showError()
                }
                else -> {

                }
            }
        }
    }
    private fun populateData(list: List<Message>) {
        Log.d(TAG, "populateData")
        messageAdapter.clear()
        list.forEach {
            if (it.from?.id == user?.id) {
                messageAdapter.add(ReceiveMessageItem(it))
            } else {
                messageAdapter.add(SendMessageItem(it))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
        viewModel.chatHistory.removeObserver(chatHistoryObserver)
        viewModel.state.removeObserver(stateObserver)

    }
    private fun receiveAutoResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            val receive = Message()
            receive.message = "Привет с того света"
            val from = From()
            from.id = user?.id
            receive.from = from

            viewModel.chatHistory.postValue(mutableListOf(receive))

        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // TODO: Use the ViewModel
    }


    private fun showError() {
        Log.i(TAG,"showError")
        binding.errorMessageText.visibility = View.VISIBLE
        binding.errorMessageText.setOnClickListener {
            viewModel.updateChatHistory(user?.id!!)
        }

        binding.recyclerView.visibility = View.INVISIBLE
        binding.button.visibility = View.INVISIBLE
        binding.editText.visibility = View.INVISIBLE
        binding.emptyChatHistoryMessageText.visibility = View.INVISIBLE
    }

    private fun showChat() {
        Log.i(TAG,"showChat")
        binding.recyclerView.visibility = View.VISIBLE
        binding.button.visibility = View.VISIBLE
        binding.editText.visibility = View.VISIBLE

        binding.errorMessageText.visibility = View.INVISIBLE
        binding.emptyChatHistoryMessageText.visibility = View.INVISIBLE
    }

    private fun showLoading() {
        Log.i(TAG,"showLoading")

        binding.recyclerView.visibility = View.INVISIBLE
        binding.button.visibility = View.INVISIBLE
        binding.editText.visibility = View.INVISIBLE
        binding.emptyChatHistoryMessageText.visibility = View.INVISIBLE

        binding.errorMessageText.visibility = View.INVISIBLE
    }

    private fun showEmptyChat() {
        Log.i(TAG,"showEmptyChat")
        binding.recyclerView.visibility = View.VISIBLE
        binding.button.visibility = View.VISIBLE
        binding.editText.visibility = View.VISIBLE
        binding.emptyChatHistoryMessageText.visibility = View.VISIBLE

        binding.errorMessageText.visibility = View.INVISIBLE

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