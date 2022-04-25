package com.divyamoza.assesmentdemo.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.utils.AppConstant
import com.divyamoza.assesmentdemo.utils.CommonUtils
import com.divyamoza.assesmentdemo.utils.Preferences
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.indicator.DotIndicatorController

/**
 * App intro activity
 *
 * @constructor Create empty App intro activity
 */
class AppIntroActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DotIndicatorController(this)
        setIndicatorColor(Color.RED, Color.BLUE)

        // Slider 1 - Welcome
        addSlider(
            title = CommonUtils.getString(
                name = R.string.lbl_slider1_title
            ),
            description = CommonUtils.getString(
                name = R.string.lbl_slider1_desc
            ),
            imageDrawable = R.drawable.ic_flipkart_logo
        )

        // Slider 2 - Home Screen
        addSlider(
            title = CommonUtils.getString(
                name = R.string.lbl_slider2_title
            ),
            description = CommonUtils.getString(
                name = R.string.lbl_slider2_desc
            ),
            imageDrawable = R.drawable.img_listing
        )

        // Slider 3 - Gadget Detail Screen
        addSlider(
            title = CommonUtils.getString(
                name = R.string.lbl_slider3_title
            ),
            description = CommonUtils.getString(
                name = R.string.lbl_slider3_desc
            ),
            imageDrawable = R.drawable.img_gadget_detail
        )

        // Slider 4 - Cart Screen
        addSlider(
            title = CommonUtils.getString(
                name = R.string.lbl_slider4_title
            ),
            description = CommonUtils.getString(
                name = R.string.lbl_slider4_desc
            ),
            imageDrawable = R.drawable.img_add_to_cart
        )

        // Slider 5 - Order Placed Screen
        addSlider(
            title = CommonUtils.getString(
                name = R.string.lbl_slider5_title
            ),
            description = CommonUtils.getString(
                name = R.string.lbl_slider5_desc
            ),
            imageDrawable = R.drawable.img_order_place
        )

        // Slider  6 - Beautiful Animations
        addSlider(
            title = CommonUtils.getString(
                name = R.string.lbl_slider6_title
            ),
            description = CommonUtils.getString(
                name = R.string.lbl_slider6_desc
            ),
            imageDrawable = R.drawable.img_animations
        )

        val appIntroComplete =
            Preferences.getPreferenceBoolean(context = this, key = AppConstant.PREF_APP_INTRO_DONE)

        if (appIntroComplete) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }


    /**
     * On skip pressed
     *
     * @param currentFragment
     */
    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        doWhenOnSkipAndOnDone()
    }


    /**
     * On done pressed
     *
     * @param currentFragment
     */
    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        doWhenOnSkipAndOnDone()
    }


    /**
     * Add slider
     *
     * @param title
     * @param description
     * @param imageDrawable
     */
    private fun addSlider(title: String, description: String?, imageDrawable: Int) {
        addSlide(
            AppIntroFragment.createInstance(
                title = title,
                description = description,
                imageDrawable = imageDrawable,
                titleColorRes = R.color.purple_500,
                descriptionColorRes = R.color.purple_700,
                backgroundColorRes = R.color.grey_shimmer_layout,

                )
        )
    }


    /**
     * Do when on skip and on done
     *
     */
    private fun doWhenOnSkipAndOnDone() {
        startActivity(Intent(this, HomeActivity::class.java))
        Preferences.setBooleanPreference(
            context = this,
            key = AppConstant.PREF_APP_INTRO_DONE,
            value = true
        )
        finish()
    }
}