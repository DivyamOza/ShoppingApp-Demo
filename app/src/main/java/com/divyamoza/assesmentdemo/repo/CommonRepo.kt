package com.divyamoza.assesmentdemo.repo

import androidx.lifecycle.MutableLiveData
import com.divyamoza.assesmentdemo.base.AssesmentDemoApp
import com.divyamoza.assesmentdemo.data.ApiClient
import com.divyamoza.assesmentdemo.data.Resource
import com.divyamoza.assesmentdemo.models.responses.CommonResponse
import com.divyamoza.assesmentdemo.models.responses.GadgetsResponse
import com.divyamoza.assesmentdemo.ui.activities.HomeActivity
import com.divyamoza.assesmentdemo.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


/**
 * Common repo
 *
 * @constructor Create empty Common repo
 */
class CommonRepo {
    private var gadgetsResponse: MutableLiveData<Resource<GadgetsResponse>> = MutableLiveData()


    init {
        gadgetsResponse.value = Resource.loading(null)
    }

    suspend fun getGadgets(activity: HomeActivity): MutableLiveData<Resource<GadgetsResponse>> {
        if (NetworkUtils.isNetworkAvailable(AssesmentDemoApp.context)) {
            val response = withContext(Dispatchers.IO) {
                ApiClient.service.getGadgets()
            }

            // Check the Response
            response.let { res ->
                if (res.isSuccessful) {
                    Timber.d("GadgetsResponse: ${res.body()}")
                    gadgetsResponse.value = Resource.success(res.body())
                } else {
                    gadgetsResponse.value = Resource.error(
                        commonResponse = CommonResponse(
                            responseCode = response.code().toString(),
                            errorMessage = response.message(), data = ""
                        )
                    )
                }
            }
        } else {
            //Todo: Add No Internet Toast Here
            Timber.e("No Internet!!!")
        }
        return gadgetsResponse
    }
}