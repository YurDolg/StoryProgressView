package com.erhn.ftknft.storyprogress.progressview

import java.util.concurrent.TimeUnit

interface ProgressViewPlayer {

    fun start()

    fun pause()

    fun resume()

    fun end()

    fun setDuration(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS)

    fun setListener(listener: ProgressView.ProgressViewListener)
}