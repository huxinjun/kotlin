package cn.xzbenben.hilt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class DaggerTest2Activity : AppCompatActivity(), TestUII, TestUII2 {


    @Inject
    lateinit var presenter: TestPresenter

    @Inject
    lateinit var presenter2: TestPresenter2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        DaggerTest2Component
//            .builder()
//            .testPresenter(TestPresenter(this))
//            .testPresenter2(TestPresenter2(this))
//            .build()
//            .injectTo(this)

        DaggerTest2Component.builder()
            .testPresenter(TestPresenter(this))
            .testPresenter2(TestPresenter2(this))
            .build()
            .injectTo(this)

        presenter.testData()
        presenter2.testData2()
    }


    override fun onData(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun onData() {
        Toast.makeText(this, "onData", Toast.LENGTH_SHORT).show()
    }

}