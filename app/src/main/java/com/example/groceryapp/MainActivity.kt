package com.example.groceryapp

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),GroceryRVAdapter.GroceryItemClickInterface {
    lateinit var itrmsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModal:GroceryViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itrmsRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)
        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list, this)
        itrmsRV.layoutManager = LinearLayoutManager(this)
        itrmsRV.adapter = groceryRVAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModal = ViewModelProvider(this,factory).get(GroceryViewModal::class.java)
        groceryViewModal.getAllGroceryItems().observe(this, Observer {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        })
        addFAB.setOnClickListener {

        }

    }


    fun openDialog() {
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn= dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt =dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdt =dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val itemQualityEdt =dialog.findViewById<EditText>(R.id.idEditItemQuantity)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName:String=itemEdt.text.toString()
            val itemPrice:String=itemPriceEdt.text.toString()
            val itemQuality:String=itemQualityEdt.text.toString()
            val qty:Int=itemQuality.toInt()
            val pr:Int=itemPrice.toInt()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty()&& itemQuality.isNotEmpty()){
                val items=GroceryItems(itemName,qty,pr)
                groceryViewModal.insert(items)
                Toast.makeText(applicationContext,"item Inserted..",Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext,"Please Enter All The Data..",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()

    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModal.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"ItemDeleted..",Toast.LENGTH_SHORT).show()
    }
}