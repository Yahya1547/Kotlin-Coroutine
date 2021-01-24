package com.example.threading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.threading.databinding.ActivityMainBinding
import com.example.threading.injection.DaggerMainComponent
import com.example.threading.viewmodel.MainViewModel
import kotlinx.coroutines.*
import java.lang.Runnable
import java.math.BigInteger
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO + job

  private lateinit var job: Job
  private lateinit var mBinding: ActivityMainBinding
  private val mViewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(mBinding.root)
    DaggerMainComponent.create().injectMainActivity(this)
    job = Job()
    initView()
  }

  private fun initView() {
    with(mBinding) {
      Glide.with(this@MainActivity).asGif().load(R.raw.spiderman).into(ivPerformer)

      /*-- place button click listener below --*/
      btNormal.setOnClickListener {
        val input = etInput.text?.toString()?.toInt() ?: 1
        val result = calculateFactorialInMainThread(input)
        setFactorialResult(result)
      }

      btnNewThread.setOnClickListener {
//        calculateInThread()
        calculateWithCoroutine()
      }

      btnLoadingSimulator.setOnClickListener {
        val intent = Intent(this@MainActivity, LoadingSimulatorActivity::class.java)
        startActivity(intent)
      }
      btnLoadingSimulatorCoroutine.setOnClickListener {
        val intent = Intent(this@MainActivity, CoroutineActivity::class.java)
        startActivity(intent)
      }
      /*-- end of button click listener --*/
    }
  }

  private fun calculateFactorialInMainThread(number: Int): BigInteger {
    var factorial = BigInteger.ONE
    for (i in 1 .. number) {
      factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
    }
    return factorial
  }

  private fun calculateFactorialWithCoroutine(number: Int): BigInteger{
    return calculateFactorialInMainThread(number)
  }

  private fun setFactorialResult(number: BigInteger) {
    mBinding.tvResult.text = "$number".substring(0,9)
  }

  private fun calculateInThread(){
    val input = mBinding.etInput.text?.toString()?.toInt() ?: 1
    Thread(Runnable{
      val result = calculateFactorialInMainThread(input)
      runOnUiThread {
        setFactorialResult(result)
      }
    }).start()
  }

  private fun calculateWithCoroutine(){
    val input = mBinding.etInput.text?.toString()?.toInt() ?: 1
    launch {
      var result = calculateFactorialWithCoroutine(input)
      withContext(Dispatchers.Main){
        setFactorialResult(result)
      }
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }

}