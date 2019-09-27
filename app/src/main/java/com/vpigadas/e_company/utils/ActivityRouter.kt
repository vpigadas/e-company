package com.vpigadas.e_company.utils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

class ActivityRouter(private val activity: AppCompatActivity) {

    private val bundle = Bundle()

    private var requestCode: Int = Integer.MIN_VALUE

    fun addRequestCode(value: Int): ActivityRouter {
        requestCode = value
        return this
    }

    fun addBundle(key: String, value: Any): ActivityRouter {
        when (value) {
            is Int -> bundle.putInt(key, value)
            is String -> bundle.putString(key, value)
            is Boolean -> bundle.putBoolean(key, value)
            is Parcelable -> bundle.putParcelable(key, value)
        }

        return this
    }

    fun <T : AppCompatActivity> startIntent(className: Class<T>) {
        if (activity.isFinishing || activity.isDestroyed) {
            return
        }

        val intent = Intent(activity, className).apply {
            putExtras(bundle)
        }

        if (requestCode == Integer.MIN_VALUE) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }
}