package com.example.groceryapp
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.selects.SelectBuilder
import kotlinx.coroutines.selects.select

@Dao
interface GroceryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )

    fun insert(item:GroceryItems)


    @Delete
    fun delete(item:GroceryItems)

    @Query("SELECT*FROM grocery_items")

    fun getAllGroceryItems(): LiveData<List<GroceryItems>>

}