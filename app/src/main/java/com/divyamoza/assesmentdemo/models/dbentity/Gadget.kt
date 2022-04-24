package com.divyamoza.assesmentdemo.models.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.divyamoza.assesmentdemo.utils.AppConstant

/**
 * Gadget
 *
 * @property id
 * @property image_url
 * @property name
 * @property price
 * @property rating
 * @constructor Create empty Gadget
 */
@Entity(tableName = AppConstant.DB_TABLE_NAME)
data class Gadget(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val image_url: String? = "",
    val name: String = "",
    val price: String = "",
    val rating: Int? = 0
)
