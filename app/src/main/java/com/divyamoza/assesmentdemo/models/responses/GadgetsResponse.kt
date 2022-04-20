package com.divyamoza.assesmentdemo.models.responses

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
    @SerializedName("image_url") val name: String? = "",
    @SerializedName("price") val price: String? = "",
    @SerializedName("rating") val rating: Int? = 0
) {
    override fun toString(): String {
        return "Product(image_url=$image_url, name=$name, price=$price, rating=$rating)"
    }
}

