package com.bhongj.rc_week4

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bhongj.rc_week4.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.conlayWhl.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

//        val runnable = Runnable {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        handler.postDelayed(runnable, 3000)
//
//        binding.animationView.setOnClickListener(){
//            handler.removeCallbacks(runnable)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        val handler = Handler(Looper.getMainLooper())

        Thread() {
            val params = binding.imgChar.layoutParams
            var cnt = 0
            val div = 2
            while(true) {
                if (cnt % div == 0) {
                    handler.post {
                        ObjectAnimator.ofFloat(binding.imgChar, View.SCALE_X, 1f).apply {
                            duration = 500L
                            start()
                        }
                        ObjectAnimator.ofFloat(binding.imgChar, View.SCALE_Y, 1f).apply {
                            duration = 500L
                            start()
                        }
                        ObjectAnimator.ofFloat(binding.splImgArrow, View.TRANSLATION_X, 10f).apply {
                            duration = 500L
                            start()
                        }
                        ObjectAnimator.ofFloat(binding.splImgArrow, View.TRANSLATION_Y, 10f).apply {
                            duration = 500L
                            start()
                        }
                    }
                }
                else if (cnt % div == 1) {
                    handler.post {
                        ObjectAnimator.ofFloat(binding.imgChar, View.SCALE_X, 1.1f).apply {
                            duration = 500L
                            start()
                        }
                        ObjectAnimator.ofFloat(binding.imgChar, View.SCALE_Y, 1.1f).apply {
                            duration = 500L
                            start()
                        }
                        ObjectAnimator.ofFloat(binding.splImgArrow, View.TRANSLATION_X, -10f).apply {
                            duration = 500L
                            start()
                        }
                        ObjectAnimator.ofFloat(binding.splImgArrow, View.TRANSLATION_Y, -10f).apply {
                            duration = 500L
                            start()
                        }
                    }
                }
                Thread.sleep(500)
                cnt++
                if (cnt > div-1) {
                    cnt = 0
                }
            }
        }.start()

        Thread() {
            var cnt = 0
            val div = 4
            while(true) {
                if (cnt % div == 0) {
                    handler.post {
                        binding.splTxtLoading.text = "Loading   "
                        binding.imgMoney1.visibility = View.INVISIBLE
                        binding.imgMoney2.visibility = View.INVISIBLE
                        binding.imgMoney3.visibility = View.INVISIBLE
                    }
                }
                else if (cnt % div == 1) {
                    handler.post {
                        binding.splTxtLoading.text = "Loading.  "
                        binding.imgMoney1.visibility = View.VISIBLE
                        binding.imgMoney2.visibility = View.INVISIBLE
                        binding.imgMoney3.visibility = View.INVISIBLE
                    }
                }
                else if (cnt % div == 2) {
                    handler.post {
                        binding.splTxtLoading.text = "Loading.. "
                        binding.imgMoney1.visibility = View.VISIBLE
                        binding.imgMoney2.visibility = View.VISIBLE
                        binding.imgMoney3.visibility = View.INVISIBLE
                    }
                }
                else if (cnt % div == 3) {
                    handler.post {
                        binding.splTxtLoading.text = "Loading..."
                        binding.imgMoney1.visibility = View.VISIBLE
                        binding.imgMoney2.visibility = View.VISIBLE
                        binding.imgMoney3.visibility = View.VISIBLE
                    }
                }
                Thread.sleep(1000)
                cnt++
                if (cnt > div-1) {
                    cnt = 0
                }
            }
        }.start()
    }
}