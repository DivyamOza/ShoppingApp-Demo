package com.divyamoza.assesmentdemo.models.dbentity

import androidx.lifecycle.LiveData
import androidx.room.*
import com.divyamoza.assesmentdemo.utils.AppConstant


/**
 * Gadget dao
 *
 * @constructor Create empty Gadget dao
 */
@Dao
interface GadgetDao {

    /**
     * Add gadget
     *
     * @param gadget
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGadget(gadget: Gadget)

    /**
     * Update gadget
     *
     * @param gadget
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGadget(gadget: Gadget)

    /**
     * Delete gadget
     *
     * @param gadget
     */
    @Delete()
    suspend fun deleteGadget(gadget: Gadget)

    /**
     * Get all gadgets
     *
     * @return
     */
    @Query("SELECT * FROM ${AppConstant.DB_TABLE_NAME}")
    fun getAllGadgets(): LiveData<List<Gadget?>>?

    /**
     * Get gadgets count
     *
     * @return
     */
    @Query("SELECT COUNT(${AppConstant.name}) FROM ${AppConstant.DB_TABLE_NAME}")
    fun getGadgetsCount(): LiveData<Int?>?
}
