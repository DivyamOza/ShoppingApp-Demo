package com.divyamoza.assesmentdemo.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.divyamoza.assesmentdemo.R
import com.divyamoza.assesmentdemo.adapters.CartRecyclerAdapter
import com.divyamoza.assesmentdemo.base.BaseActivity
import com.divyamoza.assesmentdemo.databinding.ActivityCartBinding
import com.divyamoza.assesmentdemo.databinding.ItemActionBarBackstackBinding
import com.divyamoza.assesmentdemo.listeners.BackNavigation
import com.divyamoza.assesmentdemo.models.dbentity.Gadget
import com.divyamoza.assesmentdemo.models.dbentity.GadgetDatabase
import com.divyamoza.assesmentdemo.utils.CommonUtils
import com.divyamoza.assesmentdemo.utils.ProgressBarUtils
import com.divyamoza.assesmentdemo.viewmodels.CommonViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Cart activity
 *
 * @constructor Create empty Cart activity
 */
class CartActivity : BaseActivity(), BackNavigation {
    lateinit var binding: ActivityCartBinding
    lateinit var bindingItemActionBarBackstackBinding: ItemActionBarBackstackBinding
    private lateinit var commonViewModel: CommonViewModel
    private val progressBar: ProgressBarUtils = ProgressBarUtils
    lateinit var database: GadgetDatabase
    var gadgetListFromDB: MutableList<Gadget?>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        bindingItemActionBarBackstackBinding =
            DataBindingUtil.setContentView(this, R.layout.item_action_bar_backstack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        bindingItemActionBarBackstackBinding.lifecycleOwner = this
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        database = GadgetDatabase.getDatabase(context = this)
        progressBar.showProgressBar(ctx = this)
        setDataToAppBar()
        setObserver()
        getDataFromDataBase()
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
                progressBar.dismissProgressBar()
                gadgetListFromDB?.clear()
                gadgetListFromDB?.addAll(it)
            } else {
                gadgetListFromDB?.clear()
                progressBar.dismissProgressBar()
            }
            setDataToRecyclerView()
        }
    }


    /**
     * Set data to recycler view
     *
     */
    private fun setDataToRecyclerView() {
        Timber.d("@@> gadgetListFromDB: ${gadgetListFromDB?.isEmpty()}")
        if (gadgetListFromDB?.isEmpty() == true) {
            handleVisibility(wantToVisibleRecyclerView = false)
        } else {
            handleVisibility(wantToVisibleRecyclerView = true)
            gadgetListFromDB?.toList()?.let {
                val cartRecyclerAdapter =
                    CartRecyclerAdapter(gadgetsList = it, commonViewModel = commonViewModel)
                binding.rvCart.adapter = cartRecyclerAdapter
            }
            var totalPrice = 0
            gadgetListFromDB?.toList()?.forEach { it ->
                totalPrice = totalPrice + it?.price.toString().toInt() ?: 0
            }

            binding.totalPrice = totalPrice.toString() ?: ""
        }

    }


    /**
     * Get data from data base
     *
     */
    private fun getDataFromDataBase() {
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val entireDB = database.gadgetDao().getAllGadgets()
            entireDB?.value?.let { gadgetListFromDB?.addAll(it) }
            Timber.d("entireData: ${entireDB?.value.toString()}")
        }
    }


    /**
     * Handle visibility
     *
     * @param wantToVisibleRecyclerView
     */
    private fun handleVisibility(wantToVisibleRecyclerView: Boolean) {
        if (wantToVisibleRecyclerView) {
            binding.rvCart.visibility = View.VISIBLE
            binding.clPlaceOrder.visibility = View.VISIBLE
            binding.animEmptyCart.visibility = View.GONE
        } else {
            binding.rvCart.visibility = View.GONE
            binding.clPlaceOrder.visibility = View.GONE
            binding.animEmptyCart.visibility = View.VISIBLE
        }
    }


    /**
     * Set data to app bar
     *
     */
    private fun setDataToAppBar() {
        binding.listener = this
        binding.title = CommonUtils.getString(name = R.string.lbl_my_cart)
    }


    /**
     * Navigate to back stack
     *
     */
    override fun navigateToBackStack() {
        onBackPressed()
    }
}