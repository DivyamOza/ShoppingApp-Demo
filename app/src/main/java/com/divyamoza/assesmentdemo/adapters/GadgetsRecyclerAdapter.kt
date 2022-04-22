package com.divyamoza.assesmentdemo.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.divyamoza.assesmentdemo.databinding.ItemGadgetsBinding
import com.divyamoza.assesmentdemo.models.responses.Product
import com.divyamoza.assesmentdemo.ui.activities.GadgetDetailActivity
import com.divyamoza.assesmentdemo.utils.AppConstant
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
                val i =
                    Intent(itemGadgetsBinding.mainLayout.context, GadgetDetailActivity::class.java)
                val currentGadget = gadgetsList[adapterPosition]
                i.putExtra(AppConstant.name, currentGadget.name)
                i.putExtra(AppConstant.price, currentGadget.price)
                i.putExtra(AppConstant.rating, currentGadget.rating.toString())
                i.putExtra(AppConstant.image_url, currentGadget.image_url)
                itemGadgetsBinding.mainLayout.context.startActivity(i)

            }
        }
    }

}