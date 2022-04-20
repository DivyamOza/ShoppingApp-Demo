package com.divyamoza.assesmentdemo.utils

import android.app.AlertDialog
import android.content.Context
import com.divyamoza.assesmentdemo.R


/**
 * Common Progress Bar
 * Created By: Divyam Oza
 */
object ProgressBarUtils {
    private lateinit var dialog: AlertDialog

    /**
     * Show progress bar
     *
     * @param ctx
     */
    fun showProgressBar(ctx: Context?) {
        val builder = AlertDialog.Builder(ctx)
        builder.setCancelable(false)
        builder.setView(R.layout.item_progress_dialog)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }


    /**
     * Dismiss progress bar
     *
     */
    fun dismissProgressBar() {
        dialog.dismiss()
    }
}
