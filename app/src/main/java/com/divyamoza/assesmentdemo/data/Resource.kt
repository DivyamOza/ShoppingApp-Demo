package com.divyamoza.assesmentdemo.data

import com.divyamoza.assesmentdemo.models.responses.CommonResponse


/**
 * Resource
 *
 * @param T
 * @property status
 * @property data
 * @property commonResponse
 * @constructor Create empty Resource
 */
data class Resource<out T>(val status: Status, val data: T?, val commonResponse: CommonResponse?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(commonResponse: CommonResponse): Resource<T> {
            return Resource(Status.ERROR, null, commonResponse)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

/**
 * Status
 *
 * @constructor Create empty Status
 */
enum class Status {
    /**
     * Success
     *
     * @constructor Create empty Success
     */
    SUCCESS,

    /**
     * Error
     *
     * @constructor Create empty Error
     */
    ERROR,

    /**
     * Loading
     *
     * @constructor Create empty Loading
     */
    LOADING
}
