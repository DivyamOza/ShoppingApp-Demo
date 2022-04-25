package com.divyamoza.assesmentdemo.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {

    /**
     * @param context - pass context
     * @return SharedPreferences
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            AppConstant.PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    /**
     * Store string value in preference
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param val     - String value to be stored
     */
    fun setPreference(context: Context, key: String, value: String) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Store float value in preference
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param value     - String value to be stored
     */
    fun setFloatPreference(context: Context, key: String, value: Float) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * Store boolean value in preference
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param value     - String value to be stored
     */
    fun setBooleanPreference(context: Context, key: String, value: Boolean) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * Store int value in preference
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param value     - String value to be stored
     */
    fun setIntPreference(context: Context, key: String, value: Int) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * Add preferences
     *
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param value     - long value to be stored, mostly used to store FB Session value
     */
    fun setLongPreference(context: Context, key: String, value: Long) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * Add preferences
     *
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param ArrayList<String> val    - ArrayList<String> value to be stored, mostly used to store FB Session value
    </String></String> */

    fun setPreferenceArray(mContext: Context, key: String, array: ArrayList<String>): Boolean {
        val prefs = getSharedPreferences(mContext)
        val editor = prefs.edit()
        editor.putInt(key + "_size", array.size)
        for (i in 0 until array.size)
            editor.putString(key + "_" + i, array[i])
        return editor.commit()
    }


    fun clearPreferenceArray(c: Context, key: String) {
        val settings = getSharedPreferences(c)

        if (getPreferenceArray(c, key) != null && getPreferenceArray(c, key)!!.size > 0) {
            for (element in getPreferenceArray(c, key)!!) {
                if (findPreferenceKey(c, element) != null && settings.contains(
                        findPreferenceKey(
                            c,
                            element
                        )
                    )
                ) {
                    val editor = settings.edit()
                    editor.remove(findPreferenceKey(c, element))
                    editor.apply()
                }
            }
        }
    }

    private fun findPreferenceKey(con: Context, value: String): String? {
        val settings = getSharedPreferences(con)

        val editor = settings.all

        for ((key, value1) in editor) {
            if (value == value1) {
                return key
            }
        }
        return null // not found
    }

    /**
     * Remove preference key
     *
     * @param context - context
     * @param key     - the key which you stored before
     */
    fun removePreference(context: Context, key: String) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * Get preference value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return - String value
     */
    fun getPreference(context: Context, key: String): String {
        val prefs = getSharedPreferences(context)
        return prefs.getString(key, "").toString()
    }

    /**
     * Get preference ArrayList<String> value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return - ArrayList<String> value
    </String></String> */

    private fun getPreferenceArray(context: Context, key: String): ArrayList<String>? {
        val prefs = getSharedPreferences(context)
        val size = prefs.getInt(key + "_size", 0)
        val array = ArrayList<String>(size)
        for (i in 0 until size)
            array.add(prefs.getString(key + "_" + i, null).toString())
        return array
    }


    /**
     * Get preference value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return - long value
     */
    fun getPreferenceLong(context: Context, key: String): Long {
        val prefs = getSharedPreferences(context)
        return prefs.getLong(key, 0)

    }

    fun getPreferenceBoolean(context: Context, key: String): Boolean {
        val prefs = getSharedPreferences(context)
        return prefs.getBoolean(key, false)
    }

    fun getPreferenceInt(context: Context, key: String): Int {
        val prefs = getSharedPreferences(context)
        return prefs.getInt(key, 0)
    }

    /**
     * Clear all stored  preferences
     *
     * @param context - context
     */
    fun removeAllPreference(context: Context) {
        val settings = getSharedPreferences(context)
        val editor = settings.edit()
        editor.clear()
        editor.apply()
    }

    /**
     * Clear all stored  preferences
     *
     * @param context - context
     */
    fun getAllPreference(context: Context): String {
        val settings = getSharedPreferences(context)
        val editor = settings.all
        var text = ""

        try {
            for ((key, value) in editor) {
                text += "\t$key = $value\t"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return text
    }
}