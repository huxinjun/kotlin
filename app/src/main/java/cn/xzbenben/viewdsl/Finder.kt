package cn.xzbenben.viewdsl

import android.util.Log
import android.util.SparseArray
import android.view.View
import cn.xzbenben.kotlintest.R
import java.lang.Exception
import java.lang.RuntimeException


@Target(AnnotationTarget.FIELD)//表示可以在函数中使用
@Retention(AnnotationRetention.RUNTIME)//表示运行时注解
annotation class Bind(val id: Int)


/**
 * view finder
 * Created by xinjun on 2020/7/7 10:26
 */
open class Finder(protected var v: View) {

    private var history = SparseArray<View>()

    fun <T : View> find(id: Int): T {
        @Suppress("UNCHECKED_CAST")
        if (history[id] != null)
            return history[id] as T
        val v: T = v.findViewById(id)
            ?: throw RuntimeException("finder not find any view by id ${searchIdNameFromR(id)}")
        history.put(id, v)
        return v
    }

    fun <T : View> find(id: Int, function: T.() -> Unit) {
        val find = find<T>(id)
        find.function()
    }

}


inline fun finder(v: View, function: Finder.() -> Unit): Finder {
    val finder = Finder(v)
    finder.function()
    return finder
}

inline fun <T : Finder> finder(factory: T, function: T.() -> Unit): T {

    factory::class.java.declaredFields.forEach {
        it?.run {
            (it::setAccessible)(true)
            val bindAnno = it.getAnnotation(Bind::class.java) ?: return@forEach
            val view = factory.find<View>(bindAnno.id)
            Log.i("xinjun", "finder.view=$view")
            Log.i("xinjun", "${it.type}")
            Log.i("xinjun", "${view::class.java}")
            try {
                it.set(factory, view)
            } catch (e: Exception) {
                throw RuntimeException("view type different,id=${searchIdNameFromR(bindAnno.id)},declare view type[${it.type}],find view type[${view::class.java}]")
            }

        }
    }
    factory.function()
    return factory
}


fun searchIdNameFromR(id: Int): String {
    R.id::class.java.declaredFields.forEach {
        (it::setAccessible)(true)
        val rId = it.getInt(null)
        if (id == rId)
            return "R.id." + it.name
    }
    return id.toString()
}
