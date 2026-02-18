package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private var onCreateTime: Long = 0
    private var onStartTimestamp: Long = 0
    private var onResumeTimestamp: Long = 0
    private var onPauseTimestamp: Long = 0
    private var onStopTimestamp: Long = 0
    private var onDestroyTimestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        onCreateTime = System.currentTimeMillis()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: start = $onCreateTime")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        onStartTimestamp = System.currentTimeMillis()
        val durationOnCreateToOnStart = onStartTimestamp - onCreateTime
        super.onStart()
        Log.d(TAG, "onStart :  разница после onCreate = ${durationOnCreateToOnStart} мс")
    }

    override fun onResume() {
        onResumeTimestamp = System.currentTimeMillis()

        val durationOnStartToOnResume = onResumeTimestamp - onStartTimestamp
        super.onResume()
        Log.d(TAG, "onResume: разница после onStart = $durationOnStartToOnResume мс ")
    }

    override fun onPause() {
        onPauseTimestamp = System.currentTimeMillis()
        val duration = onPauseTimestamp - onResumeTimestamp
        super.onPause()
        Log.d(TAG, "onPause: +${duration} мс после onResume")
    }

    override fun onStop() {
        onStopTimestamp = System.currentTimeMillis()
        val duration = onStopTimestamp - onPauseTimestamp
        super.onStop()
        Log.d(TAG, "onStop: +${duration} мс после onPause")
    }

    override fun onDestroy() {
        onDestroyTimestamp = System.currentTimeMillis()
        val duration = onDestroyTimestamp - onStopTimestamp
        super.onDestroy()
        Log.d(TAG, "onDestroy: +${duration} мс после onStop")
    }
}
