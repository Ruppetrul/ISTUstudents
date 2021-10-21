package com.chistoedet.android.istustudents.ui.main.messenger.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
class ChatFragment(var user: Staffs) : Fragment() {

    companion object {
        fun newInstance(user : Staffs) = ChatFragment(user)
    }

    private lateinit var viewModel: ChatViewModel
    private lateinit var stateObserver : Observer<ChatState>
    private lateinit var chatHistoryObserver : Observer<MutableList<Message>>

    private var _binding: ChatFragmentBinding? = null

    private val binding get() = _binding!!

    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.adapter = messageAdapter
        //receiveAutoResponse()

        (activity as AppCompatActivity?)!!.supportActionBar?.title = user.staff?.getFamily() + " " + user.staff?.getName()?.get(0) + "." + user.staff?.getPatronymic()?.get(0) + "."

        viewModel.updateChatHistory(user.id!!)
        //receiveAutoResponse()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatHistoryObserver = Observer {
            Log.d(TAG, "пришло на популяцию ${it.size}")
            populateData(it)
            //messageAdapter.notifyDataSetChanged()
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

                else -> {

                }
            }
        }
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
        viewModel.chatHistory.removeObserver(chatHistoryObserver)
        viewModel.state.removeObserver(stateObserver)

    }
    private fun receiveAutoResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            val receive = Message()
            receive.message = "Привет с того света"
            val from = From()
            from.id = user.id
            receive.from = from

            viewModel.chatHistory.postValue(mutableListOf(receive))

        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        viewModel.chatHistory.observe(viewLifecycleOwner, chatHistoryObserver)

        viewModel.state.observe(viewLifecycleOwner, stateObserver)


        // TODO: Use the ViewModel
    }


    private fun showError() {
        Log.i(TAG,"showError")
        binding.errorMessageText.visibility = View.VISIBLE

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