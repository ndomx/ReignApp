package com.ndomx.reign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedFragment = FeedFragment.newInstance(1)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, feedFragment)
        transaction.commit()
    }
}