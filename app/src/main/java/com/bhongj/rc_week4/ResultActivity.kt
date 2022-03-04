package com.bhongj.rc_week4

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bhongj.rc_week4.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val targetScore = intent.getIntExtra("targetScore", -1)
        val myScore = intent.getIntExtra("myScore", -1)
        val ballNum = intent.getIntArrayExtra("ballNum")!!
        val result = if (myScore == targetScore) {
            1
        }
        else {
            0
        }

        binding.txtTargetScore.text = "목표 : ${targetScore} "
        binding.txtMyScore.text = "점수 : ${myScore} "
        binding.imgBall1.text = "${ballNum[0]}"
        binding.imgBall2.text = "${ballNum[1]}"
        binding.imgBall3.text = "${ballNum[2]}"
        binding.imgBall4.text = "${ballNum[3]}"
        binding.imgBall5.text = "${ballNum[4]}"
        binding.imgBall6.text = "${ballNum[5]}"
        binding.imgBall7.text = "${ballNum[6]}"
        if (result == 1) {
            binding.txtSpeech.text = "우와\n다 내돈이다!"
            binding.txtSpeech.setTextColor(Color.parseColor("#000000"))
            binding.imgChar.setImageResource(R.drawable.char_win)
            binding.imgSpeech.setImageResource(R.drawable.speech_bubble_win)
            binding.imgMoney.setImageResource(R.drawable.moneytower)
        }
        else {
            binding.txtSpeech.text = "다음엔\n꼭 성공하자!"
            binding.txtSpeech.setTextColor(Color.parseColor("#999999"))
            binding.imgChar.setImageResource(R.drawable.char_lose)
            binding.imgSpeech.setImageResource(R.drawable.speech_bubble_lose)
            binding.imgMoney.setImageResource(R.drawable.moneytower)
        }
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
                    if (result == 1) {
                        handler.post {
                            binding.imgChar.setImageResource(R.drawable.char_win)
                        }
                    }
                    else {
                        handler.post {
                            binding.imgChar.setImageResource(R.drawable.char_lose)
                        }
                    }
                }
                else {
                    if (result == 1) {
                        handler.post {
                            binding.imgChar.setImageResource(R.drawable.char_win2)
                        }
                    }
                    else {
                        handler.post {
                            binding.imgChar.setImageResource(R.drawable.char_lose2)
                        }
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