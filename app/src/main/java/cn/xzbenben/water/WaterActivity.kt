package cn.xzbenben.water

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.xzbenben.kotlintest.R
import kotlinx.android.synthetic.main.activity_water.*
import org.jetbrains.anko.textColor
import org.pulp.fastapi.API
import org.pulp.fastapi.extension.SimpleObservable
import org.pulp.fastapi.model.IModel
import org.pulp.viewdsl.*
import retrofit2.http.GET


fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
    val decor: View = activity.window.decorView
    if (dark) {
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    } else {
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }
}

class WaterActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAndroidNativeLightStatusBar(this, true)

        setContentView(R.layout.activity_water)

        API.init(ApiSetting(this.applicationContext))

        API.get(this, WaterApi::class.java).getData().success {


            tv_card?.text = it.card
            tv_time.text = it.time
            tv_title.text = it.title

            rcv?.run {
                templete {
                    item { ItemSeg() }
                    footer {
                        segmentDataNullable<Any> {
                            layout(R.layout.footer_nomore)
                        }
                    }
                }
                layoutManager = LinearLayoutManager(context)

                data { it.datas!! }


            }


        }.toastError()

    }


}


class ItemSeg : Segment<Data>() {

    init {
        layout(R.layout.item_water)

        bind {

            finder.init(object : Any() {
                @Bind(R.id.tv_date)
                lateinit var tv_date: TextView

                @Bind(R.id.rcv_inner)
                lateinit var rcv_inner: RecyclerView


            }) {

                tv_date?.text = data.key

                rcv_inner.run {
                    templete {

                        item { ItemInnerSeg() }

                        layoutManager = LinearLayoutManager(context)

                    }


                    data {
                        data.items!!
                    }

                }


            }


        }


    }

}


class ItemInnerSeg : Segment<Item>() {
    init {

        layout(R.layout.item_water_inner)
        bind {

            finder.init(object : Any() {

                @Bind(R.id.tv_date)
                lateinit var tv_date: TextView

                @Bind(R.id.tv_title)
                lateinit var tv_title: TextView

                @Bind(R.id.tv_money_symbol)
                lateinit var tv_money_symbol: TextView

                @Bind(R.id.tv_money)
                lateinit var tv_money: TextView

                @Bind(R.id.tv_time)
                lateinit var tv_time: TextView

                @Bind(R.id.tv_desc)
                lateinit var tv_desc: TextView

                @Bind(R.id.tv_remain)
                lateinit var tv_remain: TextView

                @Bind(R.id.iv_line_botton)
                lateinit var iv_line_botton: View

            }) {
                data.run {

                    tv_date.text = date
                    tv_title.text = title
                    tv_money.text = money
                    tv_time.text = time
                    tv_desc.text = desc
                    tv_remain.text = "余额 ：$remain"

//                    if (pos == size - 1)
//                        iv_line_botton.visibility = View.GONE
//                    else
//                        iv_line_botton.visibility = View.VISIBLE

                    if (data.isIn) {
                        tv_money_symbol.text = "+"
                        tv_money_symbol.textColor = Color.parseColor("#fc6604")
                        tv_money.textColor = Color.parseColor("#fc6604")
                    } else {
                        tv_money_symbol.text = "-"
                        tv_money_symbol.textColor = Color.parseColor("#000000")
                        tv_money.textColor = Color.parseColor("#000000")
                    }
                }


            }


        }

    }


}


interface WaterApi {


    @GET("https://www.ttgblog.cn/interface/card.htm")
    fun getData(): SimpleObservable<Result>

}

class Result : IModel {

    var title: String? = null
    var card: String? = null
    var time: String? = null

    var datas: MutableList<Data>? = null

    override fun setCache(isCache: Boolean) {
    }


}

class Data {

    var key: String? = null
    var items: MutableList<Item>? = null

}

class Item {
    var date: String? = null
    var time: String? = null
    var title: String? = null
    var desc: String? = null
    var isIn: Boolean = false
    var money: String? = null
    var remain: String? = null
}
