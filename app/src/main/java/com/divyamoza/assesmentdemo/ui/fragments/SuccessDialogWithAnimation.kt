package com.divyamoza.assesmentdemo.ui.fragments

import android.animation.Animator
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.databinding.DialogSuccessBinding
import com.divyamoza.assesmentdemo.utils.CommonUtils
import timber.log.Timber


/**
 * Success dialog with animation
 *
 * @property isForAddToCart
 * @constructor Create empty Success dialog with animation
 */
class SuccessDialogWithAnimation() :
    DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogSuccessBinding = DataBindingUtil.inflate(
            inflater, R.layout.dialog_success, container, false
        )
        val view: View = binding.root
        dialog?.setCancelable(false)
        CommonUtils.showSuccessToast(
            requireActivity(),
            message = CommonUtils.getString(name = R.string.lbl_item_add_to_cart)
        )
        binding.animAddToCart.addAnimatorListener(object :
            Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Timber.e("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.e("Animation:", "end")
                binding.animAddToCart.visibility = View.GONE
                dialog?.dismiss()
            }

            override fun onAnimationCancel(animation: Animator?) {
                Timber.e("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Timber.e("Animation:", "repeat")
            }
        })
        return view
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // creating the fullscreen dialog
        val dialog = activity?.let { Dialog(it) }
        dialog?.window?.setBackgroundDrawableResource(R.color.black_transparent)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(root)
        dialog?.window
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return dialog ?: super.onCreateDialog(savedInstanceState)
    }
}