package com.erhn.ftknft.storyprogress.storyview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.Px
import com.erhn.ftknft.storyprogress.R
import com.erhn.ftknft.storyprogress.progressview.ProgressView

class StoryView : ViewGroup {

    private val progressViews = ArrayList<ProgressView>()

    @Px
    private var paddingBetweenProgress = 20

    private var count = 1

    private var animDuration = 7000L

    private var frontColor: Int = Color.WHITE

    private var backColor: Int = Color.BLACK

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StoryView)
        frontColor = typedArray.getColor(R.styleable.StoryView_frontColor, Color.WHITE)
        backColor = typedArray.getColor(R.styleable.StoryView_backColor, Color.BLACK)
        count = typedArray.getInt(R.styleable.StoryView_progressCount, 1)
        paddingBetweenProgress = typedArray.getDimensionPixelSize(R.styleable.StoryView_paddingBetween, 20)
        animDuration = typedArray.getInt(R.styleable.StoryView_animDuration, 7000).toLong()
        typedArray.recycle()
        initialize()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val actualSize = MeasureSpec.getSize(widthMeasureSpec)
        val actualSizeWithoutPadding = actualSize - (paddingStart + paddingEnd)
        if (progressViews.isNotEmpty()) {
            val paddingCount = progressViews.size - 1
            val paddingBetweenSpace = paddingBetweenProgress * paddingCount
            val availableSpace = actualSizeWithoutPadding - paddingBetweenSpace
            val oneProgressWidth = availableSpace / progressViews.size
            for (progressView in progressViews) {
                val m = MeasureSpec.makeMeasureSpec(oneProgressWidth, MeasureSpec.EXACTLY)
                progressView.measure(m, heightMeasureSpec)
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (progressViews.isEmpty()) return
        val start = l + paddingStart
        for (i in 0 until progressViews.size) {
            val progressView = progressViews[i]
            val left = start + (progressView.measuredWidth + paddingBetweenProgress) * i
            val right = left + progressView.measuredWidth
            progressView.layout(left, 0, right, b - paddingBottom)
        }
    }

    fun initialize() {
        removeAllViews()
        progressViews.clear()
        for (i in 0 until count) {
            val progressView = ProgressView(context)
            progressView.setDuration(animDuration)
            progressView.setFrontColor(frontColor)
            progressView.setBackColor(backColor)
            addView(progressView)
            progressViews.add(progressView)
        }
    }
}