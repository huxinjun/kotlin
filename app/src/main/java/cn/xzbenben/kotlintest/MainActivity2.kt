package cn.xzbenben.kotlintest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.pulp.viewdsl.*


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


class MainActivity2 : AppCompatActivity(), View.OnClickListener {

    var mf: MyFinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val createView = MyActivityUI2().createView(AnkoContextImpl(this, this, false))
        setContentView(createView)


        mf = finder(MyFinder(createView)) {

            btn.safe {
                text = "dwdw"
                setOnClickListener(this@MainActivity2)
            }

            rcv.safe {

                templete {

                    type {
                        when (pos) {
                            0 -> 11
                            1 -> 21
                            else -> 31
                        }
                    }

                    header {
                        SegHeader1(ctx)
                    }
                    header {
                        SegHeader2(ctx)
                    }

                    item(11) {
                        SegItem1(ctx)
                    }

                    item(21) {
                        SegItem2(ctx)
                    }
                    item(31) {
                        SegItem3(ctx)

                    }

                    footer {
                        SegFooter(ctx)
                    }
                }
            }

        }

        mf?.rcv.safe {
            data { DataTest().arr }
        }
    }

    override fun onClick(v: View?) {
        mf?.rcv.safe {
            header(0).safe {
                finder(this) {
                    find<RecyclerView>(R.id.rcv_inner) {
                        data { arrayOf("的测温", "费废物废物", "会议厅局医院").asList() }
                    }
                }
            }
            footer(0).safe {
//                finder(this) {
//                    find<TextView>(R.id.tv_txt) {
//                        text = "this is new footer text"
//                    }
//                }

                finder(object : Finder(this) {
                    @Bind(R.id.tv_txt)
                    var tv: TextView? = null
                }) {
                    tv?.text = "this is new footer text"
                }
            }
        }


    }


}

class SegHeader1(ctx: Context) : SegmentDataNullable<IT>() {
    init {
        layout(R.layout.layout1)
        bind {
            finder.find<TextView>(R.id.tv_txt) {
                text = "this is header 1"
            }

            finder.find<RecyclerView>(R.id.rcv_inner) {
                templete {
                    layoutManager = LinearLayoutManager(
                        ctx,
                        RecyclerView.HORIZONTAL,
                        false
                    )

                    item {
                        SegHeader1Item(ctx)
                    }

                }

                data { arrayOf("的", "额", "个").asList() }
            }

        }
    }
}


class SegHeader1Item(ctx: Context) : Segment<Any>() {
    init {
        layout(R.layout.layout_inner_item)
        bind {
            finder.find<TextView>(R.id.tv_abc).text = data.toString()
        }
    }
}

class SegHeader2(ctx: Context) : SegmentDataNullable<IT>() {
    init {
        layout(R.layout.layout1)
        bind {
            finder.find<TextView>(R.id.tv_txt) {
                text = "this is header 2"
            }

            finder.find<RecyclerView>(R.id.rcv_inner) {
                visibility = View.GONE
            }

        }
    }
}

class SegFooter(ctx: Context) : SegmentDataNullable<IT>() {
    init {
        layout(R.layout.layout1)
        bind {
            finder.find<TextView>(R.id.tv_txt) {
                text = "this is footer"
            }

            finder.find<RecyclerView>(R.id.rcv_inner) {
                visibility = View.GONE
            }

        }
    }
}


class SegItem1(ctx: Context) : Segment<IT0>() {
    init {
        layout(R.layout.layout1)
        bind {
            finder.find<TextView>(R.id.tv_txt).text = data?.data + data?.text
        }
    }
}

class SegItem2(ctx: Context) : Segment<IT>() {
    init {
        layout(R.layout.layout2)
        bind {
            finder.find<TextView>(R.id.tv_txt).text = data?.name + data?.value
        }
    }
}

class SegItem3(ctx: Context) : Segment<IT>() {
    init {
        layout(R.layout.layout3)
        bind {

            finder.find<TextView>(R.id.tv_txt).text = data?.name + data?.value
        }
    }
}


class MyActivityUI2 : AnkoComponent<MainActivity2> {
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun createView(ui: AnkoContext<MainActivity2>): View {
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