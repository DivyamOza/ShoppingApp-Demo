package com.divyamoza.assesmentdemo.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.utils.AppConstant


/**
 * Splash activity
 *
 * @constructor Create empty Splash activity
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        performSplashOperations()
    }


    /**
     * Perform splash operations
     *
     */
    private fun performSplashOperations() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep((AppConstant.SPLASH_TIME * 1000).toLong())
                    val i = Intent(baseContext, AppIntroActivity::class.java)
                    startActivity(i)
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        // start thread
        background.start()
    }
}