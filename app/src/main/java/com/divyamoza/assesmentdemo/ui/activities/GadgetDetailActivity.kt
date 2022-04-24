package com.divyamoza.assesmentdemo.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.databinding.ActivityGadgetDetailBinding
import com.divyamoza.assesmentdemo.models.dbentity.Gadget
import com.divyamoza.assesmentdemo.models.dbentity.GadgetDatabase
import com.divyamoza.assesmentdemo.models.responses.Product
import com.divyamoza.assesmentdemo.ui.fragments.SuccessDialogWithAnimation
import com.divyamoza.assesmentdemo.utils.AppConstant
import com.divyamoza.assesmentdemo.viewmodels.CommonViewModel
import timber.log.Timber

/**
 * Gadget detail activity
 *
 * @constructor Create empty Gadget detail activity
 */
class GadgetDetailActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityGadgetDetailBinding
    lateinit var database: GadgetDatabase
    private var gadgetName: String? = ""
    private var gadgetPrice: String? = ""
    private var gadgetRating: String? = ""
    private var gadgetImageURL: String? = ""
    private lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gadget_detail)
        binding.lifecycleOwner = this
        database = GadgetDatabase.getDatabase(context = this)
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        setListeners()
        getGadgetDetails()
        setGadgetDetails()
    }


    /**
     * Set gadget details
     *
     */
    private fun setGadgetDetails() {
        val productObject = Product(
            name = gadgetName,
            price = gadgetPrice,
            rating = gadgetRating.toString().toInt(),
            image_url = gadgetImageURL
        )
        binding.model = productObject
    }


    /**
     * Get gadget details
     *
     */
    private fun getGadgetDetails() {
        val intent = intent
        Timber.d("@@> ${intent.getStringExtra(AppConstant.name)}")
        Timber.d("@@> ${intent.getStringExtra(AppConstant.price)}")
        Timber.d("@@> ${intent.getStringExtra(AppConstant.rating)}")
        Timber.d("@@> ${intent.getStringExtra(AppConstant.image_url)}")
        if (intent.hasExtra(AppConstant.name)) {
            gadgetName = intent.getStringExtra(AppConstant.name) ?: ""
        }
        if (intent.hasExtra(AppConstant.price)) {
            gadgetPrice = intent.getStringExtra(AppConstant.price) ?: ""
        }
        if (intent.hasExtra(AppConstant.rating)) {
            gadgetRating = intent.getStringExtra(AppConstant.rating) ?: ""
        }
        if (intent.hasExtra(AppConstant.name)) {
            gadgetImageURL = intent.getStringExtra(AppConstant.image_url.trim()) ?: ""
        }
    }


    /**
     * On click
     *
     * @param v
     */
    override fun onClick(v: View?) {
        when (v) {
            // 'Add to Cart' Btn Click Event
            binding.btnAddToCart -> {
                val gadgetObjectForDB =
                    Gadget(
                        name = gadgetName ?: "",
                        price = gadgetPrice ?: "",
                        rating = gadgetRating.toString().toInt(),
                        image_url = gadgetImageURL ?: "",
                        id = 0
                    )
                commonViewModel.addGadgetIntoDB(gadget = gadgetObjectForDB)
                showSuccessDialog(isForAddToCart = true)
            }

            // 'Buy Now' Btn Click Event
            binding.btnBuy -> {
                showSuccessDialog(isForAddToCart = false)
            }
        }
    }


    /**
     * Set listeners
     *
     */
    private fun setListeners() {
        binding.btnAddToCart.setOnClickListener(this)
        binding.btnBuy.setOnClickListener(this)
    }


    /**
     * Show success dialog
     *
     * @param isForAddToCart
     */
    private fun showSuccessDialog(isForAddToCart: Boolean) {
        val successDialogWithAnimation =
            SuccessDialogWithAnimation(isForAddToCart = isForAddToCart)
        successDialogWithAnimation.show(supportFragmentManager, "customDialog")
    }
}