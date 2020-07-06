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
import cn.xzbenben.recycleview.*
import cn.xzbenben.test.toString
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity2 : AppCompatActivity(), View.OnClickListener {

    var mf: MyFinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                    header2 {
                        SegHeader1(ctx)
                    }
                    header2 {
                        SegHeader2(ctx)
                    }

                    item2(11) {
                        SegItem1(ctx)
                    }

                    item2(21) {
                        SegItem2(ctx)
                    }
                    item2(31) {
                        SegItem3(ctx)

                    }

                    footer2 {
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

class SegHeader1(ctx: Context) : Segment<IT>(ctx) {
    init {
        layout(R.layout.layout1)
        bind {
            find<TextView>(R.id.tv_txt) {
                text = "this is header 1"
            }

            find<RecyclerView>(R.id.rcv_inner) {
                templete {
                    layoutManager = LinearLayoutManager(
                        ctx,
                        RecyclerView.HORIZONTAL,
                        false
                    )

                    item2 {
                        SegHeader1Item(ctx)
                    }

                }

                data { arrayOf("的", "额", "个").asList() }
            }

        }
    }
}


class SegHeader1Item(ctx: Context) : Segment<Any>(ctx) {
    init {
        layout(R.layout.layout_inner_item)
        bind {
            find<TextView>(R.id.tv_abc).text = data.toString()
        }
    }
}

class SegHeader2(ctx: Context) : Segment<IT>(ctx) {
    init {
        layout(R.layout.layout1)
        bind {
            find<TextView>(R.id.tv_txt) {
                text = "this is header 2"
            }

            find<RecyclerView>(R.id.rcv_inner) {
                visibility = View.GONE
            }

        }
    }
}

class SegFooter(ctx: Context) : Segment<IT>(ctx) {
    init {
        layout(R.layout.layout1)
        bind {
            find<TextView>(R.id.tv_txt) {
                text = "this is footer"
            }

            find<RecyclerView>(R.id.rcv_inner) {
                visibility = View.GONE
            }

        }
    }
}


class SegItem1(ctx: Context) : Segment<IT0>(ctx) {
    init {
        layout(R.layout.layout1)
        bind {
            find<TextView>(R.id.tv_txt).text = data?.data + data?.text
        }
    }
}

class SegItem2(ctx: Context) : Segment<IT>(ctx) {
    init {
        layout(R.layout.layout2)
        bind {
            find<TextView>(R.id.tv_txt).text = data?.name + data?.value
        }
    }
}

class SegItem3(ctx: Context) : Segment<IT>(ctx) {
    init {
        layout(R.layout.layout3)
        bind {
            find<TextView>(R.id.tv_txt).text = data?.name + data?.value
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