package com.chistoedet.android.istustudents.ui.main.news

import android.annotation.SuppressLint
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vk.sdk.api.wall.dto.WallWallpostFull


class VkPagingDataSource : PagingSource<Int, WallWallpostFull>() {
    private val TAG = VkPagingDataSource::class.simpleName

    @SuppressLint("SimpleDateFormat")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallWallpostFull> {

        var basePageSize = 5
        var nextPageNumber = params.key ?: 1

        VkRepository.fetchNews(basePageSize, basePageSize * nextPageNumber).let{

            if (it.items.isNullOrEmpty()) {
                return LoadResult.Error(Exception())
            } else {

                return LoadResult.Page(
                    data = it.items,
                    prevKey = null,
                    nextKey = nextPageNumber + 1
                )
            }
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, WallWallpostFull>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}