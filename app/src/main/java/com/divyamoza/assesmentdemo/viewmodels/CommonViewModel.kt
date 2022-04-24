package com.divyamoza.assesmentdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyamoza.assesmentdemo.data.Resource
import com.divyamoza.assesmentdemo.models.dbentity.Gadget
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
    private val commonRepo: CommonRepo = CommonRepo()
    var gadgetsResponse: MutableLiveData<Resource<GadgetsResponse>> =
        MutableLiveData()
    var gadgetsCountResponse: MutableLiveData<Int?> = MutableLiveData()
    var getAllGadgetsResponse: MutableLiveData<List<Gadget?>>? = MutableLiveData()


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

    //----------------------------//
    // Database Operations Methods
    //----------------------------//


    /**
     * Add gadget into DB
     *
     * @param gadget
     */
    fun addGadgetIntoDB(gadget: Gadget) {
        viewModelScope.launch {
            commonRepo.addGadgetIntoDB(gadget = gadget)
        }
    }

    /**
     * Update gadget into DB
     *
     * @param gadget
     */
    fun updateGadgetIntoDB(gadget: Gadget) {
        viewModelScope.launch {
            commonRepo.updateGadgetIntoDB(gadget = gadget)
        }
    }

    /**
     * Delete gadget from DB
     *
     * @param gadget
     */
    fun deleteGadgetFromDB(gadget: Gadget) {
        viewModelScope.launch {
            commonRepo.deleteGadgetFromDB(gadget = gadget)
        }
    }

    /**
     * Get all gadgets from DB
     *
     */
    fun getAllGadgetsFromDB() {
        viewModelScope.launch {
            getAllGadgetsResponse?.value = listOf()
            getAllGadgetsResponse?.value =
                commonRepo.getAllGadgetsFromDB()?.value
        }
    }

    /**
     * Get gadgets counts from DB
     *
     */
    fun getGadgetsCountsFromDB() {
        viewModelScope.launch {
            gadgetsCountResponse.value = 0
            gadgetsCountResponse.value =
                commonRepo.getGadgetsCountsFromDB().value
        }
    }
}