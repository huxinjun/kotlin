package cn.xzbenben.test

import kotlinx.coroutines.*

fun postItem(item: Item): PostResult {
//    println("3")
    val token = requestToken()
//    println("4")
    val post = createPost(token, item)
//    println("4")
    val postResult = processPost(post)
//    println("5")
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
    println("main start:${Thread.currentThread().name}")
    GlobalScope.launch(Dispatchers.Unconfined) {
//        delay(1000)
        println("thread running:${Thread.currentThread().name}")
        postItem(Item())
    }
    println("main end:${Thread.currentThread().name}")
    Thread.sleep(2000)

}


