package com.chistoedet.android.istustudents.ui.main.news

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import java.text.SimpleDateFormat


sealed class VKState {
    class LoginState: VKState()
    class NotLoginState: VKState()
}

class NewsViewModel : ViewModel() {

    val news = Pager (
        PagingConfig(pageSize = 10,
        enablePlaceholders = true,
        maxSize = 100)
    ){
        VkPagingDataSource()
    }.flow
        //.cachedIn(viewModelScope)

    val state = MutableLiveData<VKState>().apply {
        postValue(VKState.NotLoginState())
    }

}

@BindingAdapter("app:text")
fun convertTime(textView: TextView, time: String) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
    Log.d("Convert", "convertTime: $time")
    val time1 = time.toLong() * 1000
    val a =  sdf.format(time1)
    textView.text = a
}