package com.example.to_dolist

import android.content.Context
import java.io.*

class FileSaver {

    val FILENAME = "listinfo.dat"

    fun writeData(item : ArrayList<String>,context : Context){
        val fos : FileOutputStream = context.openFileOutput(FILENAME,Context.MODE_PRIVATE) //No other application will read this file

        val oas = ObjectOutputStream(fos)
        oas.writeObject(item) //writes the arrayList in fos file
        oas.close()
    }

    fun readData(context: Context) : ArrayList<String>{
        var item : ArrayList<String>

        try{
            val fis : FileInputStream = context.openFileInput(FILENAME)

            val ois = ObjectInputStream(fis)
            item = ois.readObject() as ArrayList<String>
        } catch(e : FileNotFoundException){
            item = ArrayList<String>()  //creates empty arrayList to display in listview if file is not created
        }

        return item
    }
}