package com.chistoedet.android.istustudents.ui

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chistoedet.android.istustudents.Config
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

@BindingAdapter("app:text")
fun convertTime(textView: TextView, time: String) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
    Log.d("Convert", "convertTime: $time")
    val time1 = time.toLong() * 1000
    val a =  sdf.format(time1)
    textView.text = a
}

@BindingAdapter("app:src")
fun showAvatar(img: ImageView, staffs: Staffs) {

    Picasso.get().load("${Config.PHOTO_BASE_LINK}${staffs.staff?.getPhoto()}")
        //.resize(86,86)
        .resize(100,100)
        .centerCrop()
        .transform(CircularTransformation(0))

        .error(R.drawable.ic_avatar).into(img)

}