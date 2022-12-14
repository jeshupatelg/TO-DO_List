package com.example.to_dolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var item : EditText
    private lateinit var add : Button
    private lateinit var listView : ListView
    private lateinit var clr : Button
    private var itemList = ArrayList<String>()
    private var filesaver = FileSaver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item=findViewById(R.id.edtTODO)
        add=findViewById(R.id.btnADD)
        listView=findViewById(R.id.listTODO)
        clr=findViewById(R.id.btnClr)

        itemList = filesaver.readData(this)

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        //1.context  2.default layout for listView  3.default
        listView.adapter = arrayAdapter

        add.setOnClickListener{
            val itemName : String = item.text.toString()
            itemList.add(itemName)
            item.setText("")
            filesaver.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        clr.setOnClickListener{
            val alert = AlertDialog.Builder(this)
            alert.setTitle("DELETE ALL")
            alert.setMessage("Do you want to delete all items from To-Do list ?")
            alert.setCancelable(false) // prevents click on screen from cancelling this alert
            alert.setNegativeButton("NO",DialogInterface.OnClickListener{ dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("YES",DialogInterface.OnClickListener{ dialogInterface, i ->
                itemList.clear()
                arrayAdapter.notifyDataSetChanged()
                filesaver.writeData(itemList,applicationContext)
            })
            alert.create().show()
        }

        listView.setOnItemClickListener { adapterView, view, position, l ->
            val alert = AlertDialog.Builder(this)
            alert.setTitle("DELETE")
            alert.setMessage("Do you want to delete this item from To-Do list ?")
            alert.setCancelable(false) // prevents click on screen from cancelling this alert
            alert.setNegativeButton("NO",DialogInterface.OnClickListener{ dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("YES",DialogInterface.OnClickListener{ dialogInterface, i ->
                itemList.removeAt(position)    //removes from ArrayList
                arrayAdapter.notifyDataSetChanged()    //notifies arrayadapter to rewrite listview
                filesaver.writeData(itemList,applicationContext)    //rewrites in local file
            })
            alert.create().show()
        }
    }
}