package com.divyamoza.assesmentdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.divyamoza.assesmentdemo.base.AssesmentDemoApp
import com.divyamoza.assesmentdemo.databinding.ItemCartBinding
import com.divyamoza.assesmentdemo.models.dbentity.Gadget
import com.divyamoza.assesmentdemo.models.dbentity.GadgetDatabase
import com.divyamoza.assesmentdemo.utils.AppConstant
import com.divyamoza.assesmentdemo.viewmodels.CommonViewModel
import timber.log.Timber


/**
 * Cart recycler adapter
 *
 * @property gadgetsList
 * @property commonViewModel
 * @constructor Create empty Cart recycler adapter
 */
class CartRecyclerAdapter(
    private var gadgetsList: List<Gadget?>,
    private var commonViewModel: CommonViewModel
) :
    RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>() {
    var database: GadgetDatabase = GadgetDatabase.getDatabase(context = AssesmentDemoApp.context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCartBinding: ItemCartBinding =
            ItemCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(itemCartBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val gadget: Gadget? = gadgetsList[position]
        //Timber.d("gadget: $gadget")
        holder.itemCartBinding.model = gadget
        holder.itemCartBinding.executePendingBindings()
        try {
            holder.itemCartBinding.spinQty.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    id: Long
                ) {
                    val spinnerItem: String = holder.itemCartBinding.spinQty.selectedItem.toString()
                    val qty = spinnerItem.split(AppConstant.QTY_SPLITTER).toTypedArray()
                    var originalPrice = gadgetsList.get(holder.adapterPosition)?.price?.trim()
                    if (originalPrice?.contains(
                            "₹"
                        ) == true
                    ) {
                        originalPrice = originalPrice.replace(
                            "₹", ""
                        )
                    }
                    originalPrice = originalPrice?.trim()
                    Timber.d("original Price Int: ${originalPrice?.toIntOrNull()}")
                    val total: Int = qty[1].toInt() * originalPrice?.toInt()!!
                    val totalUpdatedPrice = "$total ${"₹"}"
                    holder.itemCartBinding.txtPrice.text = totalUpdatedPrice
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        } catch (e: Exception) {
            Timber.e("Exception: $e")
        }
    }

    override fun getItemCount(): Int {
        return gadgetsList.size
    }

    /**
     * Cart view holder
     *
     * @property itemCartBinding
     * @constructor Create empty Cart view holder
     */
    inner class CartViewHolder(var itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root) {
        init {
            itemCartBinding.llRemove.setOnClickListener {
                val gadgetObject = Gadget(
                    image_url = itemCartBinding?.model?.image_url ?: "",
                    name = itemCartBinding?.model?.name ?: "",
                    price = itemCartBinding?.model?.price ?: "",
                    rating = itemCartBinding?.model?.rating ?: 0,
                    id = itemCartBinding?.model?.id ?: 0
                )
                commonViewModel.deleteGadgetFromDB(gadgetObject)
            }
        }
    }

}