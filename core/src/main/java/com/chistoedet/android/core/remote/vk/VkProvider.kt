package com.chistoedet.android.core.remote.vk

interface VkProvider {

    suspend fun getAccessBool() : Boolean

}