package cn.xzbenben.kotlintest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.xzbenben.recycleview.*
import cn.xzbenben.test.toString
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    var mf: MyFinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        MyActivityUI().setContentView(this)

        val createView = MyActivityUI().createView(AnkoContextImpl(this, this, false))
        setContentView(createView)

//
//        mf = finder(MyFinder(createView)) {
//
//            btn.safe {
//                text = "dwdw"
//
//                onData<DataTest> {
//                    text = a
//                }
//            }
//
//            rcv.safe {
//
//                templete {
//
//                    header<IT> {
//                        layout(R.layout.layout1)
//                        bind {
//                            find<TextView>(R.id.tv_txt) {
//                                text = "this is header 1"
//                            }
//
//                            find<RecyclerView>(R.id.rcv_inner) {
//                                templete {
//                                    layoutManager = LinearLayoutManager(
//                                        this@MainActivity,
//                                        RecyclerView.HORIZONTAL,
//                                        false
//                                    )
//
//                                    item<String> {
//                                        layout(R.layout.layout_inner_item)
//                                        bind {
//                                            find<TextView>(R.id.tv_abc).text = data.toString()
//                                        }
//
//                                    }
//                                }
//
//                                data { arrayOf("的", "额", "个").asList() }
//                            }
//
//                        }
//                    }
//
//                    header<IT> {
//                        layout(R.layout.layout1)
//                        bind {
//                            find<TextView>(R.id.tv_txt) {
//                                text = "this is header 2"
//                            }
//                        }
//                    }
//
//                    footer<IT> {
//                        layout(R.layout.layout1)
//                        bind {
//                            find<TextView>(R.id.tv_txt).text = "this is footer 1"
//                        }
//                    }
//
//                    footer<IT> {
//                        layout(R.layout.layout1)
//                        bind {
//                            find<TextView>(R.id.tv_txt).text = "this is footer 2"
//                        }
//                    }
//
//
//
//                    type {
//                        when (pos) {
//                            0 -> 11
//                            1 -> 21
//                            else -> 31
//                        }
//                    }
//
//                    item<IT0>(11) {
//                        layout(R.layout.layout1)
//                        bind {
//                            find<TextView>(R.id.tv_txt).text = data?.data + data?.text
//                        }
//                    }
//
//
//                    item<IT>(21) {
//                        layout(R.layout.layout2)
////                            layout(TextView(ctx).id(R.id.tv_txt).color(Color.parseColor("#654321")))
//                        bind {
//                            find<TextView>(R.id.tv_txt).text = data?.name + data?.value
//                        }
//                    }
//
//                    item<IT>(31) {
//                        layout(R.layout.layout3)
////                            layout(TextView(ctx).id(R.id.tv_txt).color(Color.parseColor("#984516")))
//                        bind {
//                            find<TextView>(R.id.tv_txt).text = data?.name + data?.value
//                        }
//                    }
//                }
//
//                onData<DataTest> {
//                    data { arr }
//                }
//            }
//
//        }
//
//        val listOf = listOf(mf?.btn, mf?.rcv)
//
//        mf?.dispatch(DataTest(), listOf)
//
//
    }
}


class DataTest {
    var a = "fewfew"
    var headerData = HeaderData()
    var arr = arrayOf(IT0("test", "1"), IT("as", "2"), IT("324", "3")).asList()
//    var arr = arrayOf(1, 2, 3).asList()
}

class IT0(var data: String, var text: String)
class IT(var name: String, var value: String)
class HeaderData() {
    var a = "this is header"
}

class MyFinder(v: View) : Finder(v) {
    @Bind(1000)
    var btn: Button? = null

    @Bind(1001)
    var rcv: RecyclerView? = null
}


class MyActivityUI : AnkoComponent<MainActivity> {
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun createView(ui: AnkoContext<MainActivity>): View {
        return ui.run {
            verticalLayout {
                button("Say Hello") {
                    id = 1000
                    onClick { ctx.toast("Hello!") }

                }

                recyclerView {
                    id = 1001
                    backgroundColor = Color.parseColor("#88dedede")
                    layoutManager = LinearLayoutManager(ui.ctx)
                    overScrollMode = View.OVER_SCROLL_NEVER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                }
            }
        }
    }
}
