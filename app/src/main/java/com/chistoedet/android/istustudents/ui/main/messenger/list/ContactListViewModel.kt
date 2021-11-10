package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.core.remote.istu.ISTUProviderImpl
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = ContactListViewModel::class.simpleName

sealed class ChatListState {
    class LoadingState: ChatListState()
    class ErrorState: ChatListState()
    class ShowState: ChatListState()
    class EmptyListState: ChatListState()
    class ConnectionError: ChatListState()
}

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    var contactList = MutableLiveData<MutableList<Staffs>>()

    val state = MutableLiveData<ChatListState>().apply {
        postValue(ChatListState.LoadingState())
    }

    var api : ISTUProvider = ISTUProviderImpl()

    var shared: SharedRepositoryImpl = SharedRepositoryImpl(application)

   // var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    init {
      //  component.inject(this)
        getChatList()
    }

    internal fun getChatList() {
        val token: String? = shared.getToken()

        if (token != null) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    api.fetchChats(token).let {
                        if (it.body()?.chats != null) {
                            val students = it.body()?.chats?.students
                            val staffs = it.body()?.chats?.staffs

                            if (students != null && staffs != null) {
                                /*
                                проверяется список студентов т.к. он приходит
                                на 21.10.2021 он не реализован
                                */
                                if (students.isNotEmpty() || staffs.isNotEmpty()) {
                                    state.postValue(ChatListState.ShowState())
                                    contactList.postValue(staffs.toMutableList())
                                } else {
                                    state.postValue(ChatListState.EmptyListState())
                                }

                            } else {
                                Log.e(TAG, "getChatList: empty students or staffs list received")
                                state.postValue(ChatListState.ErrorState())
                            }

                        } else {
                            Log.e(TAG, "getChatList: empty response received")
                            state.postValue(ChatListState.ErrorState())
                        }
                    }
                } catch (e: Exception) {
                    state.postValue(ChatListState.ConnectionError())
                }
            }
        } else {
            Log.e(TAG, "getChatList: empty token")
            state.postValue(ChatListState.ErrorState())
        }
    }
}


        /*app.getToken()?.let {
            CoroutineScope(Dispatchers.Main).launch {
                component.getApiService().testChats(it).let { it ->

                    Log.d(TAG, "getChatList: ${it.body().toString()}")
            val list : MutableList<Staffs>? = it.body()?.chats?.staffs?.toMutableList()


            contactList.postValue(list!!)

            }
    }

    }*/

