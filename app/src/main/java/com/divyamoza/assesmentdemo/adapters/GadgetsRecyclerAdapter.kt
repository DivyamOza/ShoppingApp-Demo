package com.divyamoza.assesmentdemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.divyamoza.assesmentdemo.databinding.ItemGadgetsBinding
import com.divyamoza.assesmentdemo.models.responses.Product
import timber.log.Timber


/**
 * Gadgets recycler adapter
 *
 * @property gadgetsList
 * @constructor Create empty Gadgets recycler adapter
 */
class GadgetsRecyclerAdapter(private var gadgetsList: List<Product>) :
    RecyclerView.Adapter<GadgetsRecyclerAdapter.GadgetsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GadgetsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemGadgetsBinding: ItemGadgetsBinding =
            ItemGadgetsBinding.inflate(layoutInflater, parent, false)
        return GadgetsViewHolder(itemGadgetsBinding)
    }

    override fun onBindViewHolder(holder: GadgetsViewHolder, position: Int) {
        val gadget: Product = gadgetsList[position]
        Timber.d("gadget: $gadget")
        holder.itemGadgetsBinding.model = gadget
        holder.itemGadgetsBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return gadgetsList.size
    }

    /**
     * Gadgets view holder
     *
     * @property itemGadgetsBinding
     * @constructor Create empty Gadgets view holder
     */
    inner class GadgetsViewHolder(var itemGadgetsBinding: ItemGadgetsBinding) :
        RecyclerView.ViewHolder(itemGadgetsBinding.root) {

        init {
            itemGadgetsBinding.mainLayout.setOnClickListener {
                Timber.d("OnClick: ${gadgetsList[adapterPosition]}")
            }
        }
    }

}