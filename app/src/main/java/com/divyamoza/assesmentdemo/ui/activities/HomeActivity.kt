package com.divyamoza.assesmentdemo.ui.activities

import android.content.Intent
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
import com.divyamoza.assesmentdemo.databinding.ItemActionbarBinding
import com.divyamoza.assesmentdemo.listeners.NavigateToCart
import com.divyamoza.assesmentdemo.models.dbentity.GadgetDatabase
import com.divyamoza.assesmentdemo.utils.CommonUtils
import com.divyamoza.assesmentdemo.utils.NetworkUtils
import com.divyamoza.assesmentdemo.viewmodels.CommonViewModel
import timber.log.Timber


/**
 * Home activity
 *
 * @constructor Create empty Home activity
 */
class HomeActivity : BaseActivity(), NavigateToCart {
    private lateinit var commonViewModel: CommonViewModel
    lateinit var bindingItemActionBar: ItemActionbarBinding
    lateinit var binding: ActivityHomeBinding
    lateinit var database: GadgetDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingItemActionBar = DataBindingUtil.setContentView(this, R.layout.item_actionbar)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        binding.lifecycleOwner = this
        bindingItemActionBar.lifecycleOwner = this
        database = GadgetDatabase.getDatabase(context = this)
        apiCallForGadgets()
        setObserver()
        binding.listener = this
    }

    /**
     * Api call for gadgets
     *
     */
    private fun apiCallForGadgets() {
        if (NetworkUtils.isNetworkAvailable(context = AssesmentDemoApp.context)) {
            CommonUtils.startShimmerAnimation(layout = binding.shimmerViewContainer)
            commonViewModel.getGadgetsInfo(activity = this)
        } else {
            Timber.e("getGadgets(): No Internet!!")
            CommonUtils.showNoInternetToast(activity = this)
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
                        CommonUtils.stopShimmerAnimation(layout = binding.shimmerViewContainer)
                        Timber.d(CommonUtils.getString(name = R.string.lbl_success_status))
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
                        Timber.d(CommonUtils.getString(name = R.string.lbl_loading))
                    }
                    Status.ERROR -> {
                        errorOperations(errorMessage = it.commonResponse?.errorMessage)
                    }
                    else -> {
                        errorOperations(errorMessage = it.commonResponse?.errorMessage)
                    }
                }
            }
        )

        commonViewModel.gadgetsCountResponse.observe(this) {
            Timber.d("@@> countsFromDB: ${it}")
        }

        database.gadgetDao().getGadgetsCount()?.observe(this) {
            Timber.d("AllGadgetsCount: $it")
            if (it.toString().isNotBlank()) {
                binding.actionBar.gadgetsCount = it ?: 0
            } else {
                binding.actionBar.gadgetsCount = 0
            }

        }
    }


    /**
     * Method used to do error operations
     *
     * @param errorMessage
     */
    private fun errorOperations(errorMessage: String?) {
        Timber.d(CommonUtils.getString(name = R.string.lbl_error_status))
        CommonUtils.stopShimmerAnimation(layout = binding.shimmerViewContainer)
        CommonUtils.showErrorToast(
            activity = this,
            message = errorMessage
                ?: CommonUtils.getString(name = R.string.lbl_something_went_wrong)
        )
    }


    /**
     * Navigate to cart screen
     *
     */
    override fun navigateToCartScreen() {
        val i = Intent(this, CartActivity::class.java)
        startActivity(i)
    }
}