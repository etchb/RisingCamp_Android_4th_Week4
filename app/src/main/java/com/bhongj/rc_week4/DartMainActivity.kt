package com.bhongj.rc_week4

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private lateinit var randColorNum: MutableList<Int>
    private var key = 1
    private var myScore = 0
    private var targetScore = 0
    private var gameCnt = 0
    private var selNum = mutableListOf<Int>()
    private var timeCheck = 0

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDartMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.txtTime.text = "0"
        if (intent.getStringExtra("mode") == "301") {
            targetScore = 301
            binding.txtRemainScore.text = "남은 점수 : 301"
        } else {
            targetScore= (Math.random() * 21).toInt()*10 + 201 // 201 ~ 401 까지 랜덤
            binding.txtRemainScore.text = "남은 점수 : ${targetScore}"
        }

        gridLayoutID = mutableListOf<TextView>(
            binding.mainPostit1,
            binding.mainPostit2,
            binding.mainPostit3,
            binding.mainPostit4,
            binding.mainPostit5,
            binding.mainPostit6,
            binding.mainPostit7,
            binding.mainPostit8,
            binding.mainPostit9,
            binding.mainPostit10,
            binding.mainPostit11,
            binding.mainPostit12,
            binding.mainPostit13,
            binding.mainPostit14,
            binding.mainPostit15,
            binding.mainPostit16,
            binding.mainPostit17,
            binding.mainPostit18,
            binding.mainPostit19,
            binding.mainPostit20,
            binding.mainPostit21,
            binding.mainPostit22,
            binding.mainPostit23,
            binding.mainPostit24,
            binding.mainPostit25,
            binding.mainPostit26,
            binding.mainPostit27,
            binding.mainPostit28,
            binding.mainPostit29,
            binding.mainPostit30,
            binding.mainPostit31,
            binding.mainPostit32,
            binding.mainPostit33,
            binding.mainPostit34,
            binding.mainPostit35,
            binding.mainPostit36,
            binding.mainPostit37,
            binding.mainPostit38,
            binding.mainPostit39,
            binding.mainPostit40,
            binding.mainPostit41,
            binding.mainPostit42,
            binding.mainPostit43,
            binding.mainPostit44,
            binding.mainPostit45
        )
        textViewID = mutableListOf<TextView>(
            binding.imgBall1,
            binding.imgBall2,
            binding.imgBall3,
            binding.imgBall4,
            binding.imgBall5,
            binding.imgBall6,
            binding.imgBall7
        )


        binding.targetScore.text = targetScore.toString()
        binding.myScore.text = myScore.toString()

        var runLoop = false
        var shotFlag = false

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val disDen = resources.displayMetrics.density
        val moveX_s = 0f * width
        val moveX_f = 0.7f * width

        val anim_arrow =
            ObjectAnimator.ofFloat(binding.imgArrow, View.TRANSLATION_X, moveX_f).apply {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 1500L
            }
        val anim_bow = ObjectAnimator.ofFloat(binding.imgBow, View.TRANSLATION_X, moveX_f).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = 1500L
        }

        val randNum = (1..45).toMutableList().shuffled()
        randColorNum = mutableListOf()
        while (randColorNum.size < 12) {
            val tmp = (Math.random() * 45).toInt()
            if (!randColorNum.contains(tmp)) {
                randColorNum.add(tmp)
            }
        }

        for (idx in 0..44) {
            gridLayoutID[idx].text = randNum[idx].toString()
            if (randColorNum.contains(idx)) {
                if (randColorNum.indexOf(idx) % 3 == 0) {
                    gridLayoutID[idx].setBackgroundResource(R.drawable.postit_green)
                } else if (randColorNum.indexOf(idx) % 3 == 1) {
                    gridLayoutID[idx].setBackgroundResource(R.drawable.postit_yellow)
                } else if (randColorNum.indexOf(idx) % 3 == 2) {
                    gridLayoutID[idx].setBackgroundResource(R.drawable.postit_red)
                }
            } else {
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit_blue)
            }
        }

        if (gameCnt == 0) {
            Thread() {
                val handler = Handler(Looper.getMainLooper())

                Thread.sleep(100)
                handler.post {
                    binding.startCount.text = "3"
                }
                Thread.sleep(1000)
                handler.post {
                    binding.startCount.text = "2"
                }
                Thread.sleep(1000)
                handler.post {
                    binding.startCount.text = "1"
                }
                Thread.sleep(1000)
                handler.post {
                    binding.startCount.text = "Start!"
                }
                Thread.sleep(1000)
                handler.post {
                    binding.startCount.visibility = View.GONE
                }
                while (key < 1) {
                    key--
                    Thread.sleep(100)
                }
                runLoop = true
                key++
            }.start()
        }

        var moveX = 0f
        var moveY = 0f

        var pointY_s = 0f
        var pointY_f = 0f

        var time_s = 0
        var time_f = 0

        binding.imgArrow.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    runLoop = false
                    anim_arrow.cancel()
                    anim_bow.cancel()
                    pointY_s = v.y
                    moveX = v.x - event.rawX
                    moveY = v.y - event.rawY
                    time_s = System.currentTimeMillis().toInt()

                    var location = IntArray(2)
                    binding.imgArrow.getLocationOnScreen(location)

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

                    time_f = System.currentTimeMillis().toInt()
                    shotFlag = true
                }
            }
            true
        }

        Thread() {
            val handler = Handler(Looper.getMainLooper())
            while (true) {
                if (gameCnt > 6) {
                    break
                }
                if (shotFlag) {
                    while (key < 1) {
                        Thread.sleep(100)
                    }
                    key--
                    shotFlag = false
                    key++
                    handler.post {
                        val anim_arrow_shot = ObjectAnimator.ofFloat(
                            binding.imgArrow,
                            View.TRANSLATION_Y,
                            (pointY_s - pointY_f) * 2 * (4000 - min(time_f - time_s, 2000)) / 2000
                        ).apply {
                            LinearInterpolator()
                            duration = 1000L
                            start()
                        }
                    }
                    Thread.sleep(1500)
                    handler.post {
                        val pixelOff = 4
                        binding.rootlay.addView(
                            myView(
                                binding.imgArrow.x + binding.imgArrow.width / 2,
                                binding.imgArrow.y + (pixelOff * disDen),
                                binding.root.context
                            )
                        )
                        binding.imgShotEffect.x =
                            binding.imgArrow.x + binding.imgArrow.width / 2 - binding.imgShotEffect.width / 2
                        binding.imgShotEffect.y =
                            binding.imgArrow.y + (pixelOff * disDen) - binding.imgShotEffect.height / 2
                        binding.imgShotEffect.visibility = View.VISIBLE
                        val shotPos = getGridPosition(
                            binding.imgArrow.x + binding.imgArrow.width / 2,
                            binding.imgArrow.y + pixelOff * disDen
                        )
                        binding.imgArrow.y = pointY_s
                        binding.imgBow.x = 0f
                        binding.imgArrow.x = 0f

                        val idx = shotPos[0] * 5 + shotPos[1]
                        if (((0..8).toList().contains(shotPos[0])) && ((0..4).toList()
                                .contains(shotPos[1]))
                        ) {
                            myScore += getPoint(idx)
                            textViewID[gameCnt].text = gridLayoutID[idx].text.toString()
                            selNum.add(gridLayoutID[idx].text.toString().toInt())
                        } else {
                            textViewID[gameCnt].text = "꽝"
                            textViewID[gameCnt].setBackgroundResource(R.drawable.lottery_ball_fail)
                            selNum.add(0)
                        }
                        binding.myScore.text = myScore.toString()
                        binding.txtRemainScore.text = "남은 점수 : ${targetScore - myScore}"
//
//                        binding.imgArrow.x = 0f
//                        binding.imgBow.x = 0f
                        while (key < 1) {
                            Thread.sleep(100)
                        }
                        key--
                        gameCnt++
                        key++
                    }
                    Thread.sleep(500)
                    while (key < 1) {
                        Thread.sleep(100)
                    }
                    key--
                    runLoop = true
                    key++
                } else {
                    Thread.sleep(100)
                }
            }
        }.start()

        Thread() {
            val handler = Handler(Looper.getMainLooper())

            while (true) {
                if (gameCnt > 6) {
                    break
                }
                if (runLoop) {
                    runLoop = false
                    Thread.sleep(100)
                    handler.post {
                        anim_arrow.start()
                        anim_bow.start()
                    }
                }
            }
            Thread.sleep(1000)
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("targetScore", targetScore)
            intent.putExtra("myScore", myScore)
            intent.putExtra("ballNum", selNum.toIntArray())
            startActivity(intent)
            finish()
        }.start()

        Thread() {
            var time = 0
            val handler = Handler(Looper.getMainLooper())
            while (binding.startCount.visibility != View.GONE) {
                Thread.sleep(1000)
            }
//            while(true) {
//                Thread.sleep(1000)
//                if (timeCheck == 1) {
//                    handler.post{
//                        binding.txtTime.text = time.toString()
//                    }
//                    time++
//                    if (time > 999) {
//                        time = 0
//                    }
//                }
//            }
        }.start()
    }

    private fun getPoint(idx: Int): Int {
        var getScore = 0
        if (randColorNum.contains(idx)) {
            if (randColorNum.indexOf(idx) % 3 == 0) {
                getScore = gridLayoutID[idx].text.toString().toInt() * 2
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
            } else if (randColorNum.indexOf(idx) % 3 == 1) {
                getScore = gridLayoutID[idx].text.toString().toInt() * 3
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
            } else if (randColorNum.indexOf(idx) % 3 == 2) {
                getScore = gridLayoutID[idx].text.toString().toInt() * 4
                gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
            }
        } else {
            getScore = gridLayoutID[idx].text.toString().toInt()
            gridLayoutID[idx].setBackgroundResource(R.drawable.postit1)
        }
        return getScore
    }

    private fun getGridPosition(x: Float, y: Float): Array<Int> {
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val disDen = resources.displayMetrics.density

        val item_h = height * 0.5 / 9
        val item_w = (width - (20 * disDen)) / 5

        var row = ((y - (height * 0.15 + 40 * disDen)) / item_h).toFloat()
        if (row < 0) {
            row = -1f
        }
        var col = ((x - 10 * disDen) / item_w).toInt()

        return arrayOf(row.toInt(), col)
    }

    override fun onStart() {
        super.onStart()

        timeCheck = 1
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()

        timeCheck = 0
    }

    class myView(x: Float, y: Float, context: Context) : View(context) {
        val posX: Float = x
        val posy: Float = y

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            val paint = Paint()
            paint.color = Color.RED

            canvas?.drawCircle(posX, posy, 10f, paint)
        }
    }
}