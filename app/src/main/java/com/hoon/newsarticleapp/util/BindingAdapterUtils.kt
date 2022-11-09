package com.hoon.newsarticleapp.util

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.hoon.newsarticleapp.extensions.setImageWithGlide
import com.hoon.newsarticleapp.util.Constants.DATE_PATTERN
import com.hoon.newsarticleapp.util.Constants.EMPTY_DATE
import com.hoon.newsarticleapp.util.Constants.EMPTY_TITLE
import com.hoon.newsarticleapp.util.Constants.STRING_TO_DATE_PATTERN
import java.util.*

object BindingAdapterUtils {

    @BindingAdapter("url")
    @JvmStatic
    fun setImageViewUrl(view: AppCompatImageView, url: String?) {
        url?.let {
            view.setImageWithGlide(view.context, it)
        }
    }

    @BindingAdapter("date")
    @JvmStatic
    fun setDateToTextView(view: TextView, date: String?) {
        var dateString = if (date != null) {
            val date = DateUtil.stringToDate(date, STRING_TO_DATE_PATTERN)
            DateUtil.dateToString(date, DATE_PATTERN)
        } else {
            EMPTY_DATE
        }

        view.text = dateString
    }

    @BindingAdapter("title")
    @JvmStatic
    fun setTitleToTextView(view: TextView, title: String?) {
        if (title.isNullOrEmpty()) {
            view.text = EMPTY_TITLE
        }
        else {
            view.text = title
        }
    }

}