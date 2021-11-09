package com.chistoedet.android.istustudents.ui.main.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

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