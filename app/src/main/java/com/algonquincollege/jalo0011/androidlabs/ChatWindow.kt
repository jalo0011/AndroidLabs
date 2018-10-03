package com.algonquincollege.jalo0011.androidlabs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ChatWindow : Activity() {

    val CREATION_STATEMENT = "CREATE TABLE MESSAGES ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
    " aMessage text )"
    val MESSAGE = "Hi hey hello"
    var numItems = 100
    var messages = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)

        var myList = findViewById<ListView>(R.id.myList)
        var inputText = findViewById<EditText>(R.id.inputText)
        var button = findViewById<Button>(R.id.startChat)

        button.setOnClickListener( ){
            var userTyped = inputText.getText().toString()
            messages.add(userTyped)
            inputText.setText("")

/*            setResult(88)

              finish() */
        }

        var theAdapter = MyAdapter( this)
        myList?.setAdapter( theAdapter )
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


