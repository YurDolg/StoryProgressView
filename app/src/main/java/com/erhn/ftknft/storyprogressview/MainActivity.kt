package com.erhn.ftknft.storyprogressview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.erhn.ftknft.storyprogress.progressview.ProgressView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ProgressView.ProgressViewListener, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pb.setListener(this)
        btn_start.setOnClickListener(this)
        btn_end.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
        btn_resume.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            btn_start.id -> pb.start()
            btn_pause.id -> pb.pause()
            btn_end.id -> pb.end()
            btn_resume.id -> pb.resume()
            btn_cancel.id  -> pb.cancel()

        }
    }

    override fun onStartProgress() {
        Log.d("PROGRESS_VIEW", "onStartProgress")
    }

    override fun onEndProgress() {
        Log.d("PROGRESS_VIEW", "onEndProgress")
    }

    override fun onPauseProgress() {
        Log.d("PROGRESS_VIEW", "onPauseProgress")
    }

    override fun onResumeProgress() {
        Log.d("PROGRESS_VIEW", "onResumeProgress")
    }

    override fun onCancelProgress() {
        Log.d("PROGRESS_VIEW", "onCancelProgress")
    }
}