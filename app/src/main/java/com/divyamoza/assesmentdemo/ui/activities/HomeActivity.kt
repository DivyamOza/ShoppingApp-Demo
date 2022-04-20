package com.divyamoza.assesmentdemo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.BaseActivity


/**
 * Home activity
 *
 * @constructor Create empty Home activity
 */
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}