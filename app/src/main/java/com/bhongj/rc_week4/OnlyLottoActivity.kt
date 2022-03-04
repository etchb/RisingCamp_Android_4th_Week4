package com.bhongj.rc_week4

import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bhongj.rc_week4.databinding.ActivityOnlyLottoBinding

class OnlyLottoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlyLottoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlyLottoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val randNum = mutableListOf<Int>()
//        Log.d("TEST randNum", randNum.toString())
        while (randNum.size < 7) {
            val tmp = (Math.random() * 45).toInt() + 1
            if (!randNum.contains(tmp)) {
                randNum.add(tmp)
            }
        }

        binding.imgBall1.text = "${randNum[0]}"
        binding.imgBall2.text = "${randNum[1]}"
        binding.imgBall3.text = "${randNum[2]}"
        binding.imgBall4.text = "${randNum[3]}"
        binding.imgBall5.text = "${randNum[4]}"
        binding.imgBall6.text = "${randNum[5]}"
        binding.imgBall7.text = "${randNum[6]}"

        Thread() {
            var cnt = 0
            val div = 2
            Thread.sleep(500)
            val handler = Handler(Looper.getMainLooper())
            handler.post{
                ObjectAnimator.ofFloat(binding.imgChar, View.ROTATION, -360*3f, 0f).apply {
                    duration = 1000
                    start()
                }
            }
            while(true) {
                if (cnt%div == 0) {
                    handler.post {
                        binding.imgChar.setImageResource(R.drawable.char_win)
                    }
                }
                else {
                    handler.post {
                        binding.imgChar.setImageResource(R.drawable.char_win2)
                    }
                }
                cnt++
                if (cnt > 1) {
                    cnt = 0
                }
                Thread.sleep(700)
            }
        }.start()
    }
}