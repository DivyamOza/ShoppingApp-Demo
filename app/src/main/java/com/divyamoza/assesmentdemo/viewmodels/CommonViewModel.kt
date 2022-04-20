package com.divyamoza.assesmentdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyamoza.assesmentdemo.data.Resource
import com.divyamoza.assesmentdemo.models.responses.GadgetsResponse
import com.divyamoza.assesmentdemo.repo.CommonRepo
import com.divyamoza.assesmentdemo.ui.activities.HomeActivity
import kotlinx.coroutines.launch


/**
 * Common view model
 *
 * @constructor Create empty Common view model
 */
class CommonViewModel : ViewModel() {
    var gadgetsResponse: MutableLiveData<Resource<GadgetsResponse>> =
        MutableLiveData()
    private val commonRepo: CommonRepo = CommonRepo()


    /**
     * Get gadgets info
     *
     * @param activity
     */
    fun getGadgetsInfo(activity: HomeActivity) {
        viewModelScope.launch {
            gadgetsResponse.value = Resource.loading(null)
            gadgetsResponse.value =
                commonRepo.getGadgets(activity = activity).value
        }
    }
}