package cn.xzbenben.kotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.pulp.viewdsl.data
import org.pulp.viewdsl.finder
import org.pulp.viewdsl.segment
import org.pulp.viewdsl.templete

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val from = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false)
        setContentView(from)

        var lv: ListView? = null

        finder(from) {
            find<ListView>(R.id.lv) {

                templete {

                    item {
                        segment<Int>(ctx) {
                            layoutId = R.layout.layout2
                            bind {
                                find<TextView>(R.id.tv_txt).setText(data.toString())
                            }


                        }
                    }
                }

                lv = this


            }

            find<Button>(R.id.btn) {
                onClick {
                    lv?.data(true) {
                        MutableList(5) { it }
                    }
                }
            }
        }

    }
}


