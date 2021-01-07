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
import org.pulp.viewdsl.anno.Argument
import org.pulp.viewdsl.anno.BindAuto
import retrofit2.http.GET


fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
    val decor: View = activity.window.decorView
    if (dark) {
        decor.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        decor.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

class WaterActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAndroidNativeLightStatusBar(this, true)

        setContentView(R.layout.activity_water)

        rl_to_top.visibility = View.INVISIBLE

        API.init(ApiSetting(this.applicationContext))

        API.get(this, WaterApi::class.java).getData().success { result ->


            tv_card?.text = result.card
            tv_title.text = result.title

            rcv?.run {
                template {
                    item { ItemSeg::class.java }
//                    footer {
//                        ViewResSegment::class.java.withArgs(R.layout.footer_nomore)
//                    }
                }
                layoutManager = LinearLayoutManager(context)

                data { result.datas!! }

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = layoutManager!! as LinearLayoutManager
                        val findFirstVisibleItemPosition =
                            layoutManager.findFirstVisibleItemPosition()

                        rl_to_top.visibility =
                            if (findFirstVisibleItemPosition == 0) View.INVISIBLE
                            else View.VISIBLE

                        result.datas?.let {
                            val data = it[findFirstVisibleItemPosition]
                            tv_curr_date.text = data.key
                        }
                    }
                })

            }

            rl_to_top.setOnClickListener {
                rcv.smoothScrollToPosition(0)
            }


        }.toastError()

    }


}

class ViewSegment : SegmentDataNullable<Any>() {

    @Argument
    private lateinit var rootView: View

    override fun onCreateViewInstance() = rootView
}

class ViewResSegment : SegmentDataNullable<Any>() {

    @Argument
    private var resId = 0

    override fun onCreateView() = resId
}


@BindAuto
class ItemSeg : Segment<Data>() {

    lateinit var tv_date: TextView

    lateinit var tv_in: TextView

    lateinit var tv_out: TextView

    lateinit var rcv_inner: RecyclerView

    override fun onCreateView() = R.layout.item_water

    override fun onBind(bindCtx: BindingContext<Data>) {
        bindCtx.run {
            tv_date.text = data.key
            tv_in.text = "+${data.moneyIn}"
            tv_out.text = "-${data.moneyOut}"
            rcv_inner.run {
                template {

                    item { ItemInnerSeg::class.java }

                    layoutManager = LinearLayoutManager(context)

                }

                data.items?.forEach { item ->
                    data.key?.let { key ->
                        item.dataTime = key.split("-")[0] + "-" + item.date + " " + item.time
                    }

                }

                data {
                    data.items!!
                }

            }
        }
    }

}

@Suppress("PrivatePropertyName", "PropertyName")
@BindAuto
class ItemInnerSeg : Segment<Item>() {


    lateinit var tv_title: TextView

    lateinit var tv_money_symbol: TextView

    lateinit var tv_money: TextView

    lateinit var tv_time: TextView

    lateinit var tv_desc: TextView

    lateinit var tv_remain: TextView

    lateinit var iv_line_botton: View

    override fun onCreateView() = R.layout.item_water_inner

    override fun onBind(bindCtx: BindingContext<Item>) {
        bindCtx.data.run {
            tv_title.text = title
            tv_money.text = money
            tv_time.text = dataTime
            tv_desc.text = desc
            tv_remain.text = "余额：$remain"

//                    if (pos == size - 1)
//                        iv_line_botton.visibility = View.GONE
//                    else
//                        iv_line_botton.visibility = View.VISIBLE

            if (isIn) {
                tv_money_symbol.text = "+"
                tv_money_symbol.textColor = Color.parseColor("#e9615c")
                tv_money.textColor = Color.parseColor("#e9615c")
            } else {
                tv_money_symbol.text = "-"
                tv_money_symbol.textColor = Color.parseColor("#39414d")
                tv_money.textColor = Color.parseColor("#39414d")
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
    var moneyIn: String? = null
    var moneyOut: String? = null
    var items: MutableList<Item>? = null

}

class Item {
    var date: String? = null
    var time: String? = null
    var dataTime: String? = null
    var title: String? = null
    var desc: String? = null
    var isIn: Boolean = false
    var money: String? = null
    var remain: String? = null
}
