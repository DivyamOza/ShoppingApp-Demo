package com.divyamoza.assesmentdemo.data

import com.divyamoza.assesmentdemo.models.responses.GadgetsResponse
import retrofit2.Response
import retrofit2.http.GET


/**
 * Api service
 *
 * @constructor Create empty Api service
 */
interface ApiService {
    // Gadgets API
    @GET(ApiConstants.PRODUCTS_URL)
    suspend fun getGadgets(): Response<GadgetsResponse>
}