package com.example.threading

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import com.example.threading.databinding.ActivityLoadingSimulatorBinding

class LoadingSimulatorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoadingSimulatorBinding
    private val mHandlerThread = HandlerThread("Progress")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingSimulatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mHandlerThread.start()
        binding.btnStart.setOnClickListener {
            Handler(mHandlerThread.looper).post {
                for(i in 1..100){
                    Thread.sleep(100L)
                    runOnUiThread {
                        binding.progressBar.progress = i
                    }

                }
                runOnUiThread {
                    binding.status.text = "Complete"
                }
            }
        }
    }

    override fun onDestroy() {
        mHandlerThread.quitSafely()
        super.onDestroy()
    }
}