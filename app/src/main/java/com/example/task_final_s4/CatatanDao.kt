package com.example.task_final_s4

import androidx.room.*

@Dao
interface CatatanDao{
    @Insert
    fun insertCatatan(catatan: Catatan) : Long //create new data
    @Query("SELECT * FROM Catatan") fun getAllCatatan() : List<Catatan> //view data
    @Delete
    fun deleteDataCatatan(catatan: Catatan) : Int //delete data
    @Update
    fun updateDataCatatan(catatan: Catatan) : Int //edit data
}