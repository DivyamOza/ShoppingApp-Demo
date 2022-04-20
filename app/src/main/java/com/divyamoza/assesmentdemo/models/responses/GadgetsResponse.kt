package com.divyamoza.assesmentdemo.models.responses

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.divyamoza.assesmentdemo.R
import com.google.gson.annotations.SerializedName


/**
 * Gadgets response
 *
 * @property products
 * @constructor Create empty Gadgets response
 */
data class GadgetsResponse(
    @SerializedName("products") val products: List<Product> = listOf()
) {

    /**
     * To string
     *
     * @return
     */
    override fun toString(): String {
        return "GadgetsResponse(products=$products)"
    }
}


/**
 * Product
 *
 * @property image_url
 * @property name
 * @property price
 * @property rating
 * @constructor Create empty Product
 */
data class Product(
    @SerializedName("image_url") val image_url: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("price") val price: String? = "",
    @SerializedName("rating") val rating: Int? = 0
) {

    /**
     * To string
     *
     * @return
     */
    override fun toString(): String {
        return "Product(image_url=$image_url, name=$name, price=$price, rating=$rating)"
    }

    companion object {
        /**
         * Load image from XML using DataBinding
         *
         * @param imageView
         * @param imageUrl
         */
        @JvmStatic
        @BindingAdapter("android:loadImage")
        fun loadImage(imageView: ImageView?, imageUrl: String?) {
            if (imageView != null) {
                Glide.with(imageView)
                    .load(imageUrl)
                    .fitCenter()
                    .apply(RequestOptions().error(R.drawable.ic_launcher_foreground))
                    .into(imageView)
            }
        }
    }
}

