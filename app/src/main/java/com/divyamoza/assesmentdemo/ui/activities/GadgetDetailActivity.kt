package com.divyamoza.assesmentdemo.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.databinding.ActivityGadgetDetailBinding
import com.divyamoza.assesmentdemo.models.responses.Product
import com.divyamoza.assesmentdemo.utils.AppConstant
import timber.log.Timber

/**
 * Gadget detail activity
 *
 * @constructor Create empty Gadget detail activity
 */
class GadgetDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGadgetDetailBinding
    private var gadgetName: String? = ""
    private var gadgetPrice: String? = ""
    private var gadgetRating: String? = ""
    private var gadgetImageURL: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gadget_detail)
        binding.lifecycleOwner = this
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
            binding.btnAddToCart -> {
                Toast.makeText(this, "Add To Cart", Toast.LENGTH_SHORT).show()
            }
            binding.btnBuy -> {
                Toast.makeText(this, "BUY", Toast.LENGTH_SHORT).show()
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
}