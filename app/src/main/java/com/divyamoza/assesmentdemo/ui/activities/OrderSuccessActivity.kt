package com.divyamoza.assesmentdemo.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.databinding.ActivityOrderSuccessBinding
import com.divyamoza.assesmentdemo.listeners.NavigateToHome

/**
 * Order success activity
 *
 * @constructor Create empty Order success activity
 */
class OrderSuccessActivity : BaseActivity(), NavigateToHome {
    lateinit var binding: ActivityOrderSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_success)
        binding.lifecycleOwner = this
        binding.listener = this
    }


    /**
     * Navigate to home screen
     *
     */
    override fun navigateToHomeScreen() {
        val i = Intent(this, HomeActivity::class.java)
        startActivity(i)
        finishAffinity()
    }


    /**
     * On back pressed
     *
     */
    override fun onBackPressed() {
        // Do Nothing Restricted User to perform Back Operations
    }
}