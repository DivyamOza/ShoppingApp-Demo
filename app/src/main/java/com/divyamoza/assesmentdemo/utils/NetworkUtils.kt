package com.divyamoza.assesmentdemo.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * Network utils
 *
 * @constructor Create empty Network utils
 */
class NetworkUtils {

    companion object {
        /**
         * check NetworkAvailable
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }
    }
}