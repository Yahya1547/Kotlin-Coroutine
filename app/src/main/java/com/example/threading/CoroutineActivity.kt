package com.example.threading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.threading.databinding.ActivityLoadingSimulatorBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoroutineActivity : AppCompatActivity(), CoroutineScope {
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO + job

  private lateinit var job: Job
  private lateinit var binding: ActivityLoadingSimulatorBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoadingSimulatorBinding.inflate(layoutInflater)
    setContentView(binding.root)
    job = Job()
    binding.btnStart.setOnClickListener {
      onButtonStart()
    }
  }

  private fun onButtonStart(){
    launch {
      for(i in 1..100){
        delay(100L)
        runOnUiThread{
          binding.progressBar.progress = i
        }
      }
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}