package com.algonquincollege.jalo0011.androidlabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : Activity() {

    val ACTIVITY_NAME = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var butt = findViewById<Button>(R.id.button)
        butt.setOnClickListener({
            var intent = Intent(this@MainActivity,ListItemsActivity::class.java)
            startActivityForResult(intent, 50)
        })

        var btn = findViewById<Button>(R.id.startChatBtn)
        btn.setOnClickListener({
            var intent = Intent(this@MainActivity,ChatWindow::class.java)
            startActivityForResult(intent,50)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 50)
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult")
            if(resultCode == Activity.RESULT_OK){
                val messagePassed = data?.getStringExtra("Response")
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(this,messagePassed,duration)
                toast.show()
                Log.i("ACTIVITY_NAME","Returned to StartActivity.onActivityResult")
            }
    }

    override fun onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()")
        super.onResume()

    }

    override fun onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()")
        super.onStart()

    }

    override fun onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()")
        super.onPause()

    }

    override fun onStop() {
        Log.i(ACTIVITY_NAME, "In onStop")
        super.onStop()

    }

    override fun onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy")
        super.onDestroy()

    }
}
