package com.divyamoza.assesmentdemo.models.dbentity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.divyamoza.assesmentdemo.utils.AppConstant

/**
 * Gadget database
 * Here, We used Singleton Pattern
 *
 * @constructor Create empty Gadget database
 */
@Database(entities = [Gadget::class], version = 1, exportSchema = false)
abstract class GadgetDatabase : RoomDatabase() {

    /**
     * Gadget dao
     *
     * @return
     */
    abstract fun gadgetDao(): GadgetDao

    companion object {
        @Volatile
        private var INSTANCE: GadgetDatabase? = null


        /**
         * Get database
         *
         * @param context
         * @return
         */
        fun getDatabase(context: Context): GadgetDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GadgetDatabase::class.java,
                        AppConstant.DB_NAME
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}