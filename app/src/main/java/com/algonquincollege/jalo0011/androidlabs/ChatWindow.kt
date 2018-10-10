package com.algonquincollege.jalo0011.androidlabs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.view.inputmethod.InputMethodManager

class ChatWindow : Activity() {

    val CREATION_STATEMENT = "CREATE TABLE MESSAGES ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + " aMessage text )"
    val MESSAGE = "Hi hey hello"
    var numItems = 100
    var messages = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat_window)

        var myList = findViewById<ListView>(R.id.myList)
        var inputText = findViewById<EditText>(R.id.inputText)
        var button = findViewById<Button>(R.id.startChat)

        var theAdapter = MyAdapter( this)

        var context = this

        val dhHelper = MyOpenHelper()
        val db = dhHelper.writableDatabase

        val results = db.query( dhHelper.TABLE_NAME, arrayOf( dhHelper.KEY_MESSAGE, dhHelper.KEY_ID), null, null, null, null, null, null)

        var numRows = results.getCount()
        results.moveToFirst()
        val keyIndex = results.getColumnIndex(dhHelper.KEY_MESSAGE)

        while(!results.isAfterLast){
            var thisMessage = results.getString(keyIndex)

            messages.add(thisMessage)
            results.moveToNext()
        }



        button.setOnClickListener( ){
            var userTyped = inputText.getText().toString()
            messages.add(userTyped)

            var newRow = ContentValues()

            newRow.put(dhHelper.KEY_MESSAGE, userTyped)
            db.insert(dhHelper.TABLE_NAME,"",newRow)

            inputText.setText("")
            theAdapter.notifyDataSetChanged()

            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(button.getWindowToken(),0)



/*            setResult(88)

              finish() */
        }
        myList?.setAdapter( theAdapter )

    }

    val DATABASE_NAME = "Messages.db"
    val VERSION_NUM = 2


    inner class MyOpenHelper : SQLiteOpenHelper(this@ChatWindow, DATABASE_NAME,null,VERSION_NUM){
        val KEY_ID = "_id"
        val TABLE_NAME = "ChatMessages"
        val KEY_MESSAGE = "Messages"

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT)")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME) //delete all current data
            onCreate(db)
        }
    }

    inner class MyAdapter(ctx : Context) : ArrayAdapter<String>(ctx, 0 ) {

        override fun getCount(): Int{
            return messages.size
        }

        override fun getItem(position:Int) : String {
            return messages.get(position)
        }

        override fun getItemId(position:Int) : Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View? {
            var inflater = LayoutInflater.from(parent.getContext())

            var thisRow = null as View?
            if (position % 2 == 0)
                thisRow = inflater.inflate(R.layout.chat_row_outgoing, null)
            else
                thisRow = inflater.inflate(R.layout.chat_row_incoming, null)

            var textView = thisRow.findViewById<TextView>(R.id.message_text)
            textView.setText( getItem(position) )

            return thisRow
        }

    }
}


