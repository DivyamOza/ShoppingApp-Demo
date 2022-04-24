package com.divyamoza.assesmentdemo.repo

import androidx.lifecycle.MutableLiveData
import com.divyamoza.assesmentdemo.base.AssesmentDemoApp
import com.divyamoza.assesmentdemo.data.ApiClient
import com.divyamoza.assesmentdemo.data.Resource
import com.divyamoza.assesmentdemo.models.dbentity.Gadget
import com.divyamoza.assesmentdemo.models.dbentity.GadgetDatabase
import com.divyamoza.assesmentdemo.models.responses.CommonResponse
import com.divyamoza.assesmentdemo.models.responses.GadgetsResponse
import com.divyamoza.assesmentdemo.ui.activities.HomeActivity
import com.divyamoza.assesmentdemo.utils.CommonUtils
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
    var database: GadgetDatabase = GadgetDatabase.getDatabase(context = AssesmentDemoApp.context)
    private var gadgetsResponse: MutableLiveData<Resource<GadgetsResponse>> = MutableLiveData()
    private var gadgetsCount: MutableLiveData<Int?> = MutableLiveData()
    private var getAllGadgets: MutableLiveData<List<Gadget?>>? = MutableLiveData()


    init {
        gadgetsResponse.value = Resource.loading(null)
        gadgetsCount.value = 0
        getAllGadgets?.value = listOf()
    }


    /**
     * Get gadgets
     *
     * @param activity
     * @return
     */
    suspend fun getGadgets(activity: HomeActivity): MutableLiveData<Resource<GadgetsResponse>> {
        if (NetworkUtils.isNetworkAvailable(AssesmentDemoApp.context)) {
            val response = withContext(Dispatchers.IO) {
                ApiClient.service.getGadgets()
            }

            // Check the Response
            response.let { res ->
                if (res.isSuccessful) {
                    Timber.d("GadgetsResponse: ${res.body().toString()}")
                    gadgetsResponse.value = Resource.success(res.body())
                } else {
                    gadgetsResponse.value = Resource.error(
                        commonResponse = CommonResponse(
                            responseCode = response.code().toString(),
                            errorMessage = response.message(),
                            data = ""
                        )
                    )
                }
            }
        } else {
            CommonUtils.showNoInternetToast(activity = activity)
            Timber.e("No Internet!!!")
        }
        return gadgetsResponse
    }

    //----------------------------//
    // Database Operations Methods
    //----------------------------//
    
    /**
     * Add gadget into DB
     *
     * @param gadget
     */
    suspend fun addGadgetIntoDB(gadget: Gadget) {
        withContext(Dispatchers.IO) {
            database.gadgetDao().addGadget(gadget)
        }
    }


    /**
     * Delete gadget from DB
     *
     * @param gadget
     */
    suspend fun deleteGadgetFromDB(gadget: Gadget) {
        withContext(Dispatchers.IO) {
            database.gadgetDao().deleteGadget(gadget)
        }
    }


    /**
     * Update gadget into DB
     *
     * @param gadget
     */
    suspend fun updateGadgetIntoDB(gadget: Gadget) {
        withContext(Dispatchers.IO) {
            database.gadgetDao().updateGadget(gadget)
        }
    }


    /**
     * Get all gadgets from DB
     *
     * @return
     */
    suspend fun getAllGadgetsFromDB(): MutableLiveData<List<Gadget?>>? {
        val response = withContext(Dispatchers.IO) {
            database.gadgetDao().getAllGadgets()
        }
        response?.let {
            getAllGadgets?.value = it.value
        }
        return getAllGadgets
    }


    /**
     * Get gadgets counts from DB
     *
     * @return
     */
    suspend fun getGadgetsCountsFromDB(): MutableLiveData<Int?> {
        val response = withContext(Dispatchers.IO) {
            database.gadgetDao().getGadgetsCount()
        }
        response?.let {
            gadgetsCount.value = it.value
        }
        return gadgetsCount
    }
}