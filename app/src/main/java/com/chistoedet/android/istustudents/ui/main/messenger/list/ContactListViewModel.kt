package com.chistoedet.android.istustudents.ui.main.messenger.list

import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.DataModule
import com.chistoedet.android.istustudents.network.response.chats.Staff
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = MessengerViewModel::class.simpleName
class MessengerViewModel(application: Application) : AndroidViewModel(application) {

    var component = DaggerActivityComponent.builder().dataModule(DataModule(application.applicationContext)).build()


    var contactList = MutableLiveData<MutableList<Staffs>>()

    var app = application
    init {
        component.inject(this)
        getChatList()
    }

    fun getChatList() {
        val app = (app as App)
        val token = app.getToken()
        var user = token?.let {
            CoroutineScope(Dispatchers.Main).launch {
        var list = component.getApiService().testChats(it).let {

                var list : MutableList<Staffs>? = it?.body()?.chats?.staffs?.toMutableList()
            
                list?.forEach {
                    
                    it.latestMessage?.message.apply {
                        Log.d(TAG, "getChatList: $this")
                    }
                    
                }

            contactList.postValue(list!!)

            }
    }

    }
}
}