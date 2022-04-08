package com.example.task_final_s4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Catatan::class], version = 1)
abstract class CatatanDatabase : RoomDatabase() {
    //function to call DAO interface, DAO interface must have been defined
    abstract fun catatanDao() : CatatanDao

    companion object{
        private var INSTANCE : CatatanDatabase? = null
        fun getInstance(context : Context):CatatanDatabase? {
            if (INSTANCE == null){
                synchronized(CatatanDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CatatanDatabase::class.java,"Catatan.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}