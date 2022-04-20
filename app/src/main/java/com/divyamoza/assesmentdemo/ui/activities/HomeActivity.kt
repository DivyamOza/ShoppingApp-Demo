package com.divyamoza.assesmentdemo.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.adapters.GadgetsRecyclerAdapter
import com.divyamoza.assesmentdemo.base.AssesmentDemoApp
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.data.Status
import com.divyamoza.assesmentdemo.databinding.ActivityHomeBinding
import com.divyamoza.assesmentdemo.utils.NetworkUtils
import com.divyamoza.assesmentdemo.utils.ProgressBarUtils
import com.divyamoza.assesmentdemo.viewmodels.CommonViewModel
import timber.log.Timber


/**
 * Home activity
 *
 * @constructor Create empty Home activity
 */
class HomeActivity : BaseActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var commonViewModel: CommonViewModel
    private val progressBar: ProgressBarUtils = ProgressBarUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        binding.lifecycleOwner = this
        setObserver()
        apiCallForGadgets()
    }

    /**
     * Api call for gadgets
     *
     */
    private fun apiCallForGadgets() {
        if (NetworkUtils.isNetworkAvailable(context = AssesmentDemoApp.context)) {
            progressBar.showProgressBar(ctx = this)
            commonViewModel.getGadgetsInfo(activity = this)
        } else {
            Timber.e("getGadgets(): No Internet!!")
            //ToDo: No Internet Dialog
        }
    }


    /**
     * Set observer
     *
     */
    private fun setObserver() {
        commonViewModel.gadgetsResponse.observe(
            this, androidx.lifecycle.Observer {
                when (it?.status) {
                    Status.SUCCESS -> {
                        progressBar.dismissProgressBar()
                        //ToDo: Show success msg
                        Timber.d("Success!!!")
                        val gadgetsRecyclerAdapter =
                            commonViewModel.gadgetsResponse.value?.data?.products?.let { it1 ->
                                GadgetsRecyclerAdapter(
                                    it1
                                )
                            }
                        binding.rvGadgets.layoutManager = LinearLayoutManager(this)
                        binding.rvGadgets.adapter = gadgetsRecyclerAdapter
                    }
                    Status.LOADING -> {
                        Timber.e("Loading!!!")
                    }
                    Status.ERROR -> {
                        progressBar.dismissProgressBar()
                        //ToDo: Show error msg
                        Timber.e("Error!!!")
                    }
                    else -> {
                        progressBar.dismissProgressBar()
                        Timber.e("Error!!!")
                        //ToDo: Show error msg
                    }
                }
            }
        )
    }
}