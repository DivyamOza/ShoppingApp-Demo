package com.divyamoza.assesmentdemo.models.responses

import com.google.gson.annotations.SerializedName

/**
 * Common response
 *
 * @property responseCode
 * @property errorMessage
 * @property data
 * @constructor Create empty Common response
 */
data class CommonResponse(
    @SerializedName("ResponseCode") val responseCode: String? = "",
    @SerializedName("ErrorMessage") val errorMessage: String? = "",
    @SerializedName("Data") val data: String? = ""
) {
    override fun toString(): String {
        return "CommonResponse(responseCode='$responseCode', errorMessage='$errorMessage', data='$data')"
    }
}