package com.example.animationsamles.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.util.TypedValue
import kotlinx.coroutines.*
import java.sql.Time
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView : View {

    //region Param
    /** height, width of the clock's view  */
    private var mHeight = 0

    /** height, width of the clock's view  */
    private var mWidth: Int = 0

    /** numeric numbers to denote the hours  */
    private var mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    /** spacing and padding of the clock-hands around the clock round  */
    private var mPadding = 0
    private var mNumeralSpacing = 0

    /** truncation of the heights of the clock-hands,
     * hour clock-hand will be smaller comparetively to others  */
    private var mHandTruncation = 0

    /** truncation of the heights of the clock-hands,
     * hour clock-hand will be smaller comparetively to others  */
    private var mHourHandTruncation: Int = 0

    /** others attributes to calculate the locations of hour-points  */
    private var mRadius = 0
    private var mPaint: Paint? = null
    private var mRect: Rect = Rect()

    private var isInit // it will be true once the clock will be initialized.
            = false

    private var timeState = TimeState(0, false)
        set(value) {
            if(value == field)
                return
            field = value
            timeListeners.forEach { it(value) }
        }

    private var timeListeners = mutableSetOf<(TimeState)->Unit>()
    private var currentTime = 0L
    private val mainScope = CoroutineScope(Dispatchers.Main)
    //endregion

    //region constructor
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    //endregion

    private fun init() {
        mPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50 // spacing from the circle border
            val minAttr = mHeight.coerceAtMost(mWidth)
            mRadius = minAttr / 2 - mPadding

            // for maintaining different heights among the clock-hands
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17

            isInit = true
        }

        /** draw the canvas-color */
        canvas.drawColor(Color.TRANSPARENT)

        mPaint?.reset()
        mPaint?.color = Color.WHITE
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = 4F
        mPaint?.isAntiAlias = true
        mPaint?.let {
            canvas.drawCircle(mWidth / 2F, mHeight / 2F, mRadius + mPadding - 10F, mPaint!!)
        }

        /** clock-center */
        mPaint?.style = Paint.Style.FILL
        mPaint?.let {
            canvas.drawCircle(mWidth / 2F, mHeight / 2F, 12F, mPaint!!) // the 03 clock hands will be rotated from this center point.
        }

        /** border of hours  */
        val fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics).toInt()
        mPaint!!.textSize = fontSize.toFloat() // set font size (optional)


        for (hour in mClockHours) {
            val tmp = hour.toString()
            mPaint!!.getTextBounds(tmp, 0, tmp.length, mRect) // for circle-wise bounding

            // find the circle-wise (x, y) position as mathematical rule
            val angle = Math.PI / 6 * (hour - 3)
            val x = (mWidth / 2 + cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = (mHeight / 2 + sin(angle) * mRadius + mRect.height() / 2).toInt()
            canvas.drawText(hour.toString(), x.toFloat(), y.toFloat(), mPaint!!) // you can draw dots to denote hours as alternative
        }

        val time = Time(currentTime())

        drawHandLine(canvas, time.hours.toDouble(), isHour = true, isSecond = false) // draw hours
        drawHandLine(canvas, time.minutes.toDouble(), isHour = false, isSecond = false) // draw minutes
        drawHandLine(canvas, time.seconds.toDouble(), isHour = false, isSecond = true) // draw seconds

        /** invalidate the appearance for next representation of time   */
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHandLine(canvas: Canvas, moment: Double, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius = if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        if (isSecond) mPaint!!.color = Color.YELLOW
        canvas.drawLine((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mWidth / 2 + cos(angle) * handRadius).toFloat(), (mHeight / 2 + sin(angle) * handRadius).toFloat(), mPaint!!)
    }

    //Listener оповещающий текущее состояние времени
    fun addListener(listener:(TimeState)->Unit){
        timeListeners.add(listener)
        listener(timeState)
        Log.d("LGT", "onDraw -> AnalogClockView -> addListener")
    }

    fun removeListener(listener: (TimeState) -> Unit) = run {
        mainScope.cancel()
        timeListeners.remove(listener)
        Log.d("LGT", "onDraw -> AnalogClockView -> removeListener")
    }

    fun currentTime() : Long = timeState.time

    //Старт таймера
    fun start(time: Long) {
        addSeconds(isStart = true, time)
        mainScope.launch {
            while (timeState.isPlayed) {
                addSeconds()
                delay(1000)
                Log.d("LGT", "onDraw -> AnalogClockView -> start -> while")
            }
        }
        Log.d("LGT", "onDraw -> AnalogClockView -> start")
    }

    //Остановка таймера
    fun stop() {
        timeState = TimeState(currentTime(), false)
    }

    //Сброс таймера и его остановка
    fun reset() {
        timeState = TimeState(0, false)
        currentTime = 0
    }

    private fun addSeconds(isStart: Boolean = false, time: Long = 0L) {
        if(isStart.not())
            currentTime += 1000
        if(time != 0L)
            currentTime = time
        timeState = TimeState(currentTime, isPlayed = true)
    }
}