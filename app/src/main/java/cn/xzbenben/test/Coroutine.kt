package cn.xzbenben.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun postItem(item: Item): PostResult {
    println("3")
    val token = requestToken()
    println("4")
    val post = createPost(token, item)
    println("4")
    val postResult = processPost(post)
    println("5")
    return postResult
}

fun processPost(post: Any): PostResult {

    return PostResult()

}

fun createPost(token: Any, item: Item): Any {

    return Any()
}

fun requestToken(): Any {
    return Any()
}

class Item {

}

class PostResult {

}

fun main() {
    println("1")
    GlobalScope.launch {
//        delay(1000)
        println("2")
        postItem(Item())
        println("6")
    }
    println("7")
    Thread.sleep(2000)

}
