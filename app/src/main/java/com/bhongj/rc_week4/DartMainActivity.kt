package com.bhongj.rc_week4

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import com.bhongj.rc_week4.databinding.ActivityDartMainBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.max
import kotlin.math.min

class DartMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityDartMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDartMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        Log.d("TEST width", width.toString())
        Log.d("TEST height", height.toString())
        Log.d("TEST resources.displayMetrics.density", resources.displayMetrics.density.toString())


        val moveX_s = 0f*width
        val moveX_f = 0.7f*width

        val anim_arrow_r = ObjectAnimator.ofFloat(binding.imgArrow, View.TRANSLATION_X, moveX_f).apply {
            LinearInterpolator()
            duration = 1500L
        }
        val anim_arrow_l = ObjectAnimator.ofFloat(binding.imgArrow, View.TRANSLATION_X, moveX_s).apply {
            LinearInterpolator()
            duration = 1500L
        }
        val anim_bow_r = ObjectAnimator.ofFloat(binding.imgBow, View.TRANSLATION_X, moveX_f).apply {
            LinearInterpolator()
            duration = 1500L
        }
        val anim_bow_l = ObjectAnimator.ofFloat(binding.imgBow, View.TRANSLATION_X, moveX_s).apply {
            LinearInterpolator()
            duration = 1500L
        }

        var moveX = 0f
        var moveY = 0f
        var runLoop = true
        var shotFlag = false

        var pointY_s = 0f
        var pointY_f = 0f

        var time_s = 0
        var time_f = 0

        binding.imgArrow.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    anim_arrow_r.cancel()
                    anim_arrow_l.cancel()
                    anim_bow_r.cancel()
                    anim_bow_l.cancel()
                    pointY_s = v.y
                    moveX = v.x - event.rawX
                    moveY = v.y - event.rawY
                    runLoop = false
                    Log.d("TEST v.x", v.x.toString())
                    Log.d("TEST v.y", v.y.toString())
                    Log.d("TEST event.rawX", event.rawX.toString())
                    Log.d("TEST event.rawY", event.rawY.toString())
                    time_s = System.currentTimeMillis().toInt()
                    Log.d("TEST time_s", time_s.toString())

                    var location = IntArray(2)
                    binding.imgArrow.getLocationOnScreen(location)
                    Log.d("TEST location[0]", location[0].toString())
                    Log.d("TEST location[1]", location[1].toString())
                }

                MotionEvent.ACTION_MOVE -> {
                    v.animate()
//                        .x(event.rawX + moveX)
                        .y(max(event.rawY + moveY, pointY_s))
                        .setDuration(0)
                        .start()
                }

                MotionEvent.ACTION_UP -> {
                    moveX = v.x - event.rawX
                    moveY = v.y - event.rawY
                    pointY_f = v.y
                    Log.d("TEST v.x", v.x.toString())
                    Log.d("TEST v.y", v.y.toString())
                    Log.d("TEST event.rawX", event.rawX.toString())
                    Log.d("TEST event.rawY", event.rawY.toString())
                    shotFlag = true
                    time_f = System.currentTimeMillis().toInt()
                    Log.d("TEST time_f", time_f.toString())
                    Log.d("TEST time_f-time_s", (time_f-time_s).toString())

                    Log.d("TEST binding.grdlay.left", binding.grdlay.left.toString())
                    Log.d("TEST binding.grdlay.left", binding.grdlay.right.toString())
                    Log.d("TEST binding.grdlay.left", binding.grdlay.top.toString())
                    Log.d("TEST binding.grdlay.left", binding.grdlay.bottom.toString())
                }
            }
            true
        }

        val handler = Handler(Looper.getMainLooper())

        Thread() {
            var cnt = 0
            val div = 2

            while(true) {
                if (runLoop && cnt % div == 0) {
                    handler.post {
                        anim_arrow_r.start()
                        anim_bow_r.start()
                    }
                    Thread.sleep(1600)
                }
                else if (runLoop && cnt % div == 1) {
                    handler.post {
                        anim_arrow_l.start()
                        anim_bow_l.start()
                    }
                    Thread.sleep(1600)
                }
                else {
                    Thread.sleep(100)
                }
                cnt++
                if (cnt > div-1) {
                    cnt = 0
                }
            }
        }.start()

        Thread() {
            while(true) {
                if (shotFlag) {
                    handler.post {
                        val anim_arrow_shot = ObjectAnimator.ofFloat(
                            binding.imgArrow,
                            View.TRANSLATION_Y,
                            (pointY_s - pointY_f)*2*(4000 - min(time_f-time_s,2000))/2000
                        ).apply {
                            LinearInterpolator()
                            duration = 1500L
                            start()
                        }
                    }
                    Log.d("TEST pointY_s", pointY_s.toString())
                    Log.d("TEST pointY_f", pointY_f.toString())
                    Log.d("TEST max(time_f-time_s,2000)", min(time_f-time_s,2000).toString())
                    shotFlag = false
                    Thread.sleep(1600)
                }
                else {
                    Thread.sleep(100)
                }
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()

        binding.rootlay.addView(myView("안녕하세요", binding.root.context))
    }

    class myView(text: String, context: Context) : View(context) {
        val drawValue: String
        init {
            this.drawValue = text
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            val paint = Paint()
            paint.color = Color.RED
            paint.textSize = 50f

            canvas?.drawCircle(100f,100f,100f, paint)
        }
    }
}