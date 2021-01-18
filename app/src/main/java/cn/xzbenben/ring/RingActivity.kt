package cn.xzbenben.ring

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.xzbenben.kotlintest.R
import kotlinx.android.synthetic.main.activity_ring.*

class RingActivity : AppCompatActivity() {

    var p = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
    }

    fun addProgress(view: View) {
        p += 0.3f
        progress.setProgress(p, 1000)
    }

}