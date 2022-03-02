package com.bhongj.rc_week4

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bhongj.rc_week4.databinding.ActivityDartMainBinding
import kotlin.math.max
import kotlin.math.min

class DartMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityDartMainBinding
    private lateinit var gridLayoutID: MutableList<TextView>
    private lateinit var textViewID: MutableList<TextView>
    private lateinit var randColorNum : MutableList<Int>
    private var myScore = 0
    private var targetScore = 301
    private var gameCnt = 0

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDartMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        gridLayoutID = mutableListOf<TextView>(binding.mainPostit1,binding.mainPostit2,binding.mainPostit3,binding.mainPostit4,binding.mainPostit5,binding.mainPostit6,binding.mainPostit7,binding.mainPostit8,binding.mainPostit9,binding.mainPostit10,binding.mainPostit11,binding.mainPostit12,binding.mainPostit13,binding.mainPostit14,binding.mainPostit15,binding.mainPostit16,binding.mainPostit17,binding.mainPostit18,binding.mainPostit19,binding.mainPostit20,binding.mainPostit21,binding.mainPostit22,binding.mainPostit23,binding.mainPostit24,binding.mainPostit25,binding.mainPostit26,binding.mainPostit27,binding.mainPostit28,binding.mainPostit29,binding.mainPostit30,binding.mainPostit31,binding.mainPostit32,binding.mainPostit33,binding.mainPostit34,binding.mainPostit35,binding.mainPostit36,binding.mainPostit37,binding.mainPostit38,binding.mainPostit39,binding.mainPostit40,binding.mainPostit41,binding.mainPostit42,binding.mainPostit43,binding.mainPostit44,binding.mainPostit45)
        textViewID = mutableListOf<TextView>(binding.imgBall1,binding.imgBall2,binding.imgBall3,binding.imgBall4,binding.imgBall5,binding.imgBall6,binding.imgBall7)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

//        Log.d("TEST width", width.toString())
//        Log.d("TEST height", height.toString())
//        Log.d("TEST resources.displayMetrics.density", resources.displayMetrics.density.toString())


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
//                    Log.d("TEST v.x", v.x.toString())
//                    Log.d("TEST v.y", v.y.toString())
//                    Log.d("TEST event.rawX", event.rawX.toString())
//                    Log.d("TEST event.rawY", event.rawY.toString())
                    time_s = System.currentTimeMillis().toInt()
//                    Log.d("TEST time_s", time_s.toString())

                    var location = IntArray(2)
                    binding.imgArrow.getLocationOnScreen(location)
//                    Log.d("TEST location[0]", location[0].toString())
//                    Log.d("TEST location[1]", location[1].toString())

//                    binding.rootlay.addView(myView(v.x+v.width/2, v.y, binding.root.context))

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
//                    Log.d("TEST v.x", v.x.toString())
//                    Log.d("TEST v.y", v.y.toString())
//                    Log.d("TEST event.rawX", event.rawX.toString())
//                    Log.d("TEST event.rawY", event.rawY.toString())
                    shotFlag = true
                    time_f = System.currentTimeMillis().toInt()
//                    Log.d("TEST time_f", time_f.toString())
//                    Log.d("TEST time_f-time_s", (time_f-time_s).toString())
//
//                    Log.d("TEST binding.grdlay.left", binding.grdlay.left.toString())
//                    Log.d("TEST binding.grdlay.left", binding.grdlay.right.toString())
//                    Log.d("TEST binding.grdlay.left", binding.grdlay.top.toString())
//                    Log.d("TEST binding.grdlay.left", binding.grdlay.bottom.toString())
//                    binding.rootlay.addView(myView(v.x+v.width/2, v.y, binding.root.context))
                }
            }
            true
        }

        val handler = Handler(Looper.getMainLooper())

        Thread() {
            var cnt = 0
            val div = 2

            while(true) {
                if (runLoop && (cnt % div == 0)) {
                    handler.post {
                        anim_arrow_r.start()
                        anim_bow_r.start()
                    }
                    Thread.sleep(1600)
                }
                else if (runLoop && (cnt % div == 1)) {
                    handler.post {
                        anim_arrow_l.start()
                        anim_bow_l.start()
                    }
                    Thread.sleep(1600)
                }
                else {
                    Thread.sleep(100)
                }
                if (cnt > div-1) {
                    cnt = 0
                }
                if (gameCnt > 6) {
                    break
                }
                cnt++
            }
        }.start()

        Thread() {
            while(true) {
                if (shotFlag) {
                    shotFlag = false
                    handler.post {
                        val anim_arrow_shot = ObjectAnimator.ofFloat(
                            binding.imgArrow,
                            View.TRANSLATION_Y,
                            (pointY_s - pointY_f)*2*(4000 - min(time_f-time_s,2000))/2000
                        ).apply {
                            LinearInterpolator()
                            duration = 1000L
                            start()
                        }
                    }
//                    Log.d("TEST pointY_s", pointY_s.toString())
//                    Log.d("TEST pointY_f", pointY_f.toString())
//                    Log.d("TEST max(time_f-time_s,2000)", min(time_f-time_s,2000).toString())
                    Thread.sleep(1800)
                    handler.post {
                        binding.rootlay.addView(
                            myView(
                                binding.imgArrow.x + binding.imgArrow.width / 2,
                                binding.imgArrow.y,
                                binding.root.context
                            )
                        )
                        binding.imgShotEffect.x = binding.imgArrow.x + binding.imgArrow.width / 2 - binding.imgShotEffect.width/2
                        binding.imgShotEffect.y = binding.imgArrow.y - binding.imgShotEffect.height/2
                        binding.imgShotEffect.visibility = View.VISIBLE
                        val shotPos = getGridPosition(binding.imgArrow.x + binding.imgArrow.width / 2, binding.imgArrow.y)
                        binding.imgArrow.y = pointY_s

                        val idx = shotPos[0]*5 + shotPos[1]
                        Log.d("TEST idx", idx.toString())
                        if (((0..9).toList().contains(shotPos[0])) && ((0..4).toList().contains(shotPos[1]))) {
                            myScore += getPoint(idx)
                            textViewID[gameCnt].text = gridLayoutID[idx].text.toString()
                        }
                        else {
                            Log.d("TEST 꽝", "꽝입니다")
                            textViewID[gameCnt].text = "꽝"
                        }
                        binding.myScore.text = myScore.toString()
                        gameCnt++
//                        binding.imgArrow.x = 0f
//                        binding.imgBow.x = 0f
                    }
//                    Thread.sleep(0)
                    runLoop = true
                }
                else {
                    Thread.sleep(100)
                }
                if (gameCnt > 6) {
                    break
                }
            }
        }.start()
    }

    private fun getPoint(idx: Int) : Int {
        var getScore = 0
        if (randColorNum.contains(idx)) {
            if (randColorNum.indexOf(idx)%3 == 0) {
                getScore = gridLayoutID[idx].text.toString().toInt() * 2
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
            }
            else if (randColorNum.indexOf(idx)%3 == 1) {
                getScore = gridLayoutID[idx].text.toString().toInt() * 3
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
            }
            else if (randColorNum.indexOf(idx)%3 == 2) {
                getScore = gridLayoutID[idx].text.toString().toInt() * 4
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
            }
        }
        else {
            getScore = gridLayoutID[idx].text.toString().toInt()
            gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
        }
        return getScore
    }

    private fun getGridPosition(x: Float, y: Float): Array<Int> {
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
//        Log.d("TEST width", width.toString())
//        Log.d("TEST height", height.toString())

        val item_h = height*0.5/9
        val item_w = (width-(20*resources.displayMetrics.density))/5
        Log.d("TEST item_h", item_h.toString())
        Log.d("TEST item_w", item_w.toString())

        Log.d("TEST x", x.toString())
        Log.d("TEST y", y.toString())

        Log.d("TEST height*0.15", (height*0.15).toString())
        Log.d("TEST 10*resources.displayMetrics.density", (10*resources.displayMetrics.density).toString())

        var row = ((y-(height*0.15 + 40*resources.displayMetrics.density))/item_h).toFloat()
        row = if (row < 0) {
            -1f
        } else {
            ((y-(height*0.15 + 40*resources.displayMetrics.density))/item_h).toFloat()
        }
        val col = ((x-10*resources.displayMetrics.density)/item_w).toInt()
        Log.d("TEST col", col.toString())
        Log.d("TEST row", row.toString())
        return arrayOf(row.toInt(), col)
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST gridLayoutID.size", gridLayoutID.size.toString())

        binding.targetScore.text = targetScore.toString()
        binding.myScore.text = myScore.toString()

        val randNum = (1..45).toMutableList().shuffled()
        Log.d("TEST randNum", randNum.toString())
        randColorNum = mutableListOf()
        while (randColorNum.size < 12) {
            val tmp = (Math.random() * 45).toInt()
            if (!randColorNum.contains(tmp)) {
                randColorNum.add(tmp)
            }
        }
        Log.d("TEST randColorNum", randColorNum.toString())

        for (idx in 0 .. 44) {
            gridLayoutID[idx].text = randNum[idx].toString()
            if (randColorNum.contains(idx)) {
                if (randColorNum.indexOf(idx)%3 == 0) {
                    gridLayoutID[idx].setBackgroundResource(R.drawable.postit_green)
                }
                else if (randColorNum.indexOf(idx)%3 == 1) {
                    gridLayoutID[idx].setBackgroundResource(R.drawable.postit_yellow)
                }
                else if (randColorNum.indexOf(idx)%3 == 2) {
                    gridLayoutID[idx].setBackgroundResource(R.drawable.postit_red)
                }
            }
            else {
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit_blue)
            }
        }
    }

    override fun onResume() {
        super.onResume()

//        binding.rootlay.addView(myView(100f, 100f, binding.root.context))
//        binding.rootlay.addView(myView(10*3.5f, 3100*0.15f+40*3.5f, binding.root.context))
//        binding.rootlay.removeViewAt(binding.rootlay.childCount-1)

    }

    class myView(x: Float, y:Float, context: Context) : View(context) {
        val posX: Float = x
        val posy: Float = y

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            val paint = Paint()
            paint.color = Color.RED

            canvas?.drawCircle(posX,posy,10f, paint)
        }
    }
}