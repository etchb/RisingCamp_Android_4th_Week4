package com.bhongj.rc_week4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.bhongj.rc_week4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lotteryNumbers = arrayListOf(binding.no1, binding.no2, binding.no3, binding.no4, binding.no5, binding.no6, binding.no7)

        val countDownTimer = object: CountDownTimer(3000, 100) {
            override fun onTick(p0: Long) {
                val tmpNum = mutableListOf<Int>()
                lotteryNumbers.forEach {
                    var randNum = 0
                    while(true){
                        randNum = (Math.random() * 45 + 1).toInt()
                        if (!tmpNum.contains(randNum)){
                            break
                        }
                    }
                    tmpNum.add(randNum)
                    it.text = "$randNum"
                }
            }
            override fun onFinish() {
            }
        }

        val btn = binding.btn
        btn.setOnClickListener {
            if (btn.isAnimating){
                btn.cancelAnimation()
                countDownTimer.cancel()
            }
            else {
                btn.playAnimation()
                countDownTimer.start()
            }
        }
    }
}