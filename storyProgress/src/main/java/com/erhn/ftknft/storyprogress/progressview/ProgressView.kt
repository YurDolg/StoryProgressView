package com.erhn.ftknft.storyprogress.progressview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnPause
import androidx.core.animation.doOnResume
import androidx.core.animation.doOnStart
import com.erhn.ftknft.storyprogress.utils.RoundRectF
import java.util.concurrent.TimeUnit

class ProgressView : View, ProgressViewColors, ProgressViewPlayer {

    @Px
    private var progressHeight: Int = 40


    @ColorInt
    private var frontColorInt: Int = Color.WHITE

    @ColorInt
    private var backColorInt: Int = Color.BLACK

    private var animDuration: Long = 3000L

    private var backBounds: RoundRectF = RoundRectF()

    private var frontBounds: RoundRectF = RoundRectF()

    private var mainAnimator = ValueAnimator.ofFloat(OF_FLOAT_START, OF_FLOAT_END)

    private var currentAnimatorValue: Float = 0f

    private val paint: Paint = Paint()

    private var progressListener: ProgressViewListener? = null


    constructor(context: Context?) : super(context) {
        initialize()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val actualWidth = MeasureSpec.getSize(widthMeasureSpec)
        val actualHeight = MeasureSpec.getSize(heightMeasureSpec)
        calculateBounds(actualWidth, actualHeight)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = backColorInt
        canvas.drawRoundRectF(backBounds, paint)
        paint.color = frontColorInt
        canvas.drawRoundRectF(frontBounds, paint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mainAnimator.addUpdateListener {
            currentAnimatorValue = it.animatedValue as Float
            onAnimatorUpdate()
        }

        mainAnimator.doOnStart { progressListener?.onStartProgress() }
        mainAnimator.doOnEnd { progressListener?.onEndProgress() }
        mainAnimator.doOnResume { progressListener?.onResumeProgress() }
        mainAnimator.doOnPause { progressListener?.onPauseProgress() }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainAnimator.removeAllUpdateListeners()
    }

    override fun setFrontColor(color: Int) {
        frontColorInt = color
    }

    override fun setBackColor(color: Int) {
        backColorInt = color
    }

    override fun setDuration(duration: Long, timeUnit: TimeUnit) {
        animDuration = timeUnit.toMillis(duration)
    }

    override fun start() {
        mainAnimator.start()
    }

    override fun pause() {
        mainAnimator.pause()
    }

    override fun resume() {
        mainAnimator.resume()
    }

    override fun end() {
        mainAnimator.end()
    }

    override fun setListener(listener: ProgressViewListener) {
        progressListener = listener
    }

    private fun initialize() {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        mainAnimator.duration = animDuration
        mainAnimator.interpolator = LinearInterpolator()
    }

    private fun calculateBounds(actualWidth: Int, actualHeight: Int) {
        backBounds.apply {
            left = paddingStart.toFloat()
            right = actualWidth - paddingEnd.toFloat()
            top = paddingTop.toFloat()
            bottom = actualHeight - paddingBottom.toFloat()
            rXRadius = height() / 2f
            rYRadius = rXRadius
        }
        frontBounds.apply {
            left = backBounds.left
            right = backBounds.right * currentAnimatorValue
            top = backBounds.top
            bottom = backBounds.bottom
            rXRadius = backBounds.rXRadius
            rYRadius = backBounds.rYRadius
        }
    }


    private fun onAnimatorUpdate() {
        frontBounds.right = backBounds.right * currentAnimatorValue
        invalidate()
    }


    private fun Canvas.drawRoundRectF(roundRectF: RoundRectF, paint: Paint) {
        drawRoundRect(roundRectF, roundRectF.rXRadius, roundRectF.rYRadius, paint)
    }

    companion object {
        private const val OF_FLOAT_START = 0f
        private const val OF_FLOAT_END = 1f
    }

    interface ProgressViewListener {

        fun onStartProgress()

        fun onEndProgress()

        fun onPauseProgress()

        fun onResumeProgress()

    }

}