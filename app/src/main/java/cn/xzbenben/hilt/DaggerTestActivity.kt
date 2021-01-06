package cn.xzbenben.hilt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class DaggerTestActivity : AppCompatActivity(), TestUII {


    @Inject
    lateinit var presenter: TestPresenter

//    @Inject
//    lateinit var presenter2: TestPresenter2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerTestComponent
            .builder()
            .testPresenter(TestPresenter(this))
            .build()
            .injectTo(this)

        presenter.testData()
    }

    override fun onData() {

        Toast.makeText(this, "onData", Toast.LENGTH_SHORT).show()
    }

}