package com.divyamoza.assesmentdemo.utils

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.AssesmentDemoApp
import com.divyamoza.assesmentdemo.enums.ToastType
import com.facebook.shimmer.ShimmerFrameLayout
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


object CommonUtils {
    /**
     * Get string
     *
     * @param ctx
     * @param name
     * @return
     */
    fun getString(ctx: Context = AssesmentDemoApp.context, name: Int?): String {
        return if (name != null) {
            ctx.getString(name)
        } else ""
    }

    /**
     * Show no internet toast
     *
     * @param activity
     */
    fun showNoInternetToast(activity: Activity) {
        showToast(
            activity,
            title = getString(name = R.string.lbl_no_internet),
            message = getString(name = R.string.lbl_check_connection),
            type = ToastType.WARNING
        )
    }


    /**
     * Show success toast
     *
     * @param activity
     * @param message
     */
    fun showSuccessToast(activity: Activity, message: String?) {
        showToast(
            activity,
            title = getString(name = R.string.lbl_success_status),
            message = message ?: "",
            type = ToastType.SUCCESS
        )
    }


    /**
     * Show error toast
     *
     * @param activity
     * @param message
     */
    fun showErrorToast(activity: Activity, message: String?) {
        showToast(
            activity = activity,
            title = getString(name = R.string.lbl_error_status),
            message = message ?: "",
            type = ToastType.ERROR
        )
    }


    /**
     * Show toast
     *
     * @param activity
     * @param title
     * @param message
     * @param type
     */
    private fun showToast(activity: Activity, title: String?, message: String?, type: ToastType) {
        val toastType: MotionToastStyle = getToastType(type)
        MotionToast.darkColorToast(
            context = activity,
            title = title ?: "",
            message = message ?: "",
            style = toastType,
            position = MotionToast.GRAVITY_BOTTOM,
            duration = MotionToast.SHORT_DURATION,
            font = ResourcesCompat.getFont(activity, R.font.roboto_regular)
        )
    }


    /**
     * Get toast type
     *
     * @param type
     * @return
     */
    private fun getToastType(type: ToastType): MotionToastStyle {
        when (type) {
            ToastType.SUCCESS -> {
                return MotionToastStyle.SUCCESS
            }
            ToastType.ERROR -> {
                return MotionToastStyle.ERROR
            }
            ToastType.WARNING -> {
                return MotionToastStyle.WARNING
            }
            ToastType.INFO -> {
                return MotionToastStyle.INFO
            }
            ToastType.DELETE -> {
                return MotionToastStyle.DELETE
            }
            else -> {
                return MotionToastStyle.INFO
            }
        }
    }


    /**
     * Start shimmer animation
     *
     * @param layout
     */
    fun startShimmerAnimation(layout: ShimmerFrameLayout) {
        layout.visibility = View.VISIBLE
        layout.startShimmerAnimation()
    }


    /**
     * Stop shimmer animation
     *
     * @param layout
     */
    fun stopShimmerAnimation(layout: ShimmerFrameLayout) {
        layout.stopShimmerAnimation()
        layout.visibility = View.GONE
    }
}