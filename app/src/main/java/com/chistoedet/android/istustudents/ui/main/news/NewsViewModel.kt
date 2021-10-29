package com.chistoedet.android.istustudents.ui.main.news

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig


class NewsViewModel : ViewModel() {

    val news = Pager (
        PagingConfig(pageSize = 10,
        enablePlaceholders = true,
        maxSize = 100)
    ){
        VkPagingDataSource()
    }.flow
        //.cachedIn(viewModelScope)



}