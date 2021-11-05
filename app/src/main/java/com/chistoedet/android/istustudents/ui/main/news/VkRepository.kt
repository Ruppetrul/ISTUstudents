package com.chistoedet.android.istustudents.ui.main.news

import android.util.Log
import com.chistoedet.android.istustudents.Config.Companion.VK_PUBLIC_ID
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponse

class VkRepository {

    companion object {
        fun fetchNews(pageSize: Int, offset: Int) : WallGetResponse {
            Log.d("VkRep","fetchNews")
            return VK.executeSync(
                WallService().wallGet(
                    UserId(VK_PUBLIC_ID),
                    null,
                   offset,
                    pageSize,
                    null,
                    null)
            )
        }

        fun fetchNewsFromNotify() : Unit {
            return VK.execute(
                WallService().wallGet(
                    UserId(VK_PUBLIC_ID),
                    null,
                    null,
                    null,
                    null,
                    null), object: VKApiCallback<WallGetResponse> {
                override fun success(result: WallGetResponse) {

                }
                override fun fail(error: Exception) {

                }
            }
            )
        }
    }
}