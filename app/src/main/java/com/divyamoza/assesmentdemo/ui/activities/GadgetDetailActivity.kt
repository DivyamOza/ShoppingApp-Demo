package com.divyamoza.assesmentdemo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.databinding.ActivityGadgetDetailBinding
import com.divyamoza.assesmentdemo.listeners.AddToCartOperation
import com.divyamoza.assesmentdemo.listeners.NavigateToOrderSuccess
import com.divyamoza.assesmentdemo.models.dbentity.Gadget
import com.divyamoza.assesmentdemo.models.dbentity.GadgetDatabase
import com.divyamoza.assesmentdemo.models.responses.Product
import com.divyamoza.assesmentdemo.ui.fragments.SuccessDialogWithAnimation
import com.divyamoza.assesmentdemo.utils.AppConstant
import com.divyamoza.assesmentdemo.utils.CommonUtils
import com.divyamoza.assesmentdemo.utils.ProgressBarUtils
import com.divyamoza.assesmentdemo.viewmodels.CommonViewModel
import timber.log.Timber

/**
 * Gadget detail activity
 *
 * @constructor Create empty Gadget detail activity
 */
class GadgetDetailActivity : BaseActivity(), NavigateToOrderSuccess,
    AddToCartOperation {
    lateinit var binding: ActivityGadgetDetailBinding
    lateinit var database: GadgetDatabase
    private var gadgetName: String? = ""
    private var gadgetPrice: String? = ""
    private var gadgetRating: String? = ""
    private var gadgetImageURL: String? = ""
    private lateinit var commonViewModel: CommonViewModel
    private val progressBar: ProgressBarUtils = ProgressBarUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gadget_detail)
        binding.lifecycleOwner = this
        database = GadgetDatabase.getDatabase(context = this)
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        setListeners()
        setObserver()
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
     * Set listeners
     *
     */
    private fun setListeners() {
        binding.listenerForAddToCart = this
        binding.listenerForBuyNow = this
    }


    /**
     * Show success dialog
     *
     * @param isForAddToCart
     */
    private fun showSuccessDialog() {
        val successDialogWithAnimation =
            SuccessDialogWithAnimation()
        successDialogWithAnimation.show(supportFragmentManager, "customDialog")
    }


    /**
     * Set observer
     *
     */
    private fun setObserver() {
        database.gadgetDao().getAllGadgets()?.observe(this) {
            //Timber.d("AllGadgetsCount: $it")
            Timber.d("@@> ${it.isNotEmpty()}")
            if (it.isNotEmpty()) {
                it.forEach { gadget ->
                    if (gadget?.name.equals(gadgetName, true)) {
                        binding.btnAddToCart.text =
                            CommonUtils.getString(name = R.string.lbl_go_to_cart)
                    }
                }
            } else {
                binding.btnAddToCart.text = CommonUtils.getString(name = R.string.lbl_add_to_cart)
            }
        }
    }

    /**
     * Navigate to order success screen
     *
     */
    override fun navigateToOrderSuccessScreen() {
        progressBar.showProgressBar(ctx = this)
        Handler().postDelayed({
            progressBar.dismissProgressBar()
            val i = Intent(this, OrderSuccessActivity::class.java)
            startActivity(i)
        }, AppConstant.DELAY_TO_PLACE_ORDER)
    }


    /**
     * Perform add to cart operations
     *
     */
    override fun performAddToCartOperation() {
        if (binding.btnAddToCart.text.toString().trim()
                .lowercase() == CommonUtils.getString(name = R.string.lbl_go_to_cart)
                .trim().lowercase()
        ) {
            val i = Intent(this, CartActivity::class.java)
            startActivity(i)
        } else {
            val gadgetObjectForDB =
                Gadget(
                    name = gadgetName ?: "",
                    price = gadgetPrice ?: "",
                    rating = gadgetRating.toString().toInt(),
                    image_url = gadgetImageURL ?: "",
                    id = 0
                )
            commonViewModel.addGadgetIntoDB(gadget = gadgetObjectForDB)
            showSuccessDialog()
        }
    }
}