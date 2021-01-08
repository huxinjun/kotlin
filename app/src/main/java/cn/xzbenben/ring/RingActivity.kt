package cn.xzbenben.ring

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.xzbenben.kotlintest.R
import kotlinx.android.synthetic.main.activity_ring.*

class RingActivity : AppCompatActivity() {

    var p = 720

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
        progress.progress = p
    }

    fun addProgress(view: View) {
        p += 90
        progress.setProgressWithAnim(p)
    }

}