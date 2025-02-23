package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.*

/**
 * Earlier, we have seen what is concurrency.
 * [Concurrency](app/src/main/java/com/example/kotlincoroutines/snippets/basics/030explicitWaitForGlobalScope.kt)
 *
 * Again, concurrency is an ability to run and deal with multiple tasks,
 * either using a synchronous programming (multi-threaded) or asynchronous programming (non-blocking multi-process).
 *
 * In this small practical, we will see and experience structured concurrency.
 *
 * Now, what is a structured concurrency?
 *
 * A structured concurrency means an ability to provide a hierarchy of coroutines.
 * For example, one coroutine inside another coroutine, one coroutine after another coroutine, and so on.
 *
 * So, we can provide a structure, a hierarchy to the coroutines.
 * Hence, we call it a structured concurrency.
 *
 * In a formal way, sometimes we call it parent-children, and sibling coroutines.
 */
fun main() = runBlocking {

    // `runBlocking` is a coroutine scope + builder and inside it, we can have another coroutine.
    val gJob1 = GlobalScope.launch {
        // `GlobalScope` is an independent coroutine without any relation to the parent coroutine.
        // Inside the `GlobalScope`, we can have multiple coroutines.
        // All the coroutines inside the `GlobalScope` are children of the `GlobalScope`.
        // job1 is a child of the `GlobalScope` gJob1.
        val job1 = launch {
            repeat(5) {
                println("runBlocking --> 1st GlobalScope.launch --> 1st launch --> before delay $it")
                delay(1000)
                println("runBlocking --> 1st GlobalScope.launch --> 1st launch --> after delay $it")
            }
            val job1insideJob1 = launch {
                repeat(5) {
                    println("runBlocking --> 1st GlobalScope.launch --> 1st launch --> launch --> before delay $it")
                    delay(1000)
                    println("runBlocking --> 1st GlobalScope.launch --> 1st launch --> launch --> after delay $it")
                }
            }
            job1insideJob1.join()
        }
        // job2 is a child of the `GlobalScope` gJob1, and sibling of the job1.
        val job2 = launch {
            repeat(5) {
                println("runBlocking --> 1st GlobalScope.launch --> 2nd launch --> before delay $it")
                delay(1000)
                println("runBlocking --> 1st GlobalScope.launch --> 2nd launch --> after delay $it")
            }
            launch {
                repeat(5) {
                    println("runBlocking --> 1st GlobalScope.launch --> 2nd launch --> launch --> before delay $it")
                    delay(1000)
                    println("runBlocking --> 1st GlobalScope.launch --> 2nd launch --> launch --> after delay $it")
                }
            }
        }
        println("runBlocking --> 1st GlobalScope.launch --> before join")
        job1.join()
        job2.join()
        println("runBlocking --> 1st GlobalScope.launch --> after join")
    }

    val gJob2 = GlobalScope.launch {
        val job1 = launch {
            repeat(5) {
                println("runBlocking --> 2nd GlobalScope.launch --> 1st launch --> before delay $it")
                delay(1000)
                println("runBlocking --> 2nd GlobalScope.launch --> 1st launch --> after delay $it")
            }
            launch {
                repeat(5) {
                    println("runBlocking --> 2nd GlobalScope.launch --> 1st launch --> launch --> before delay $it")
                    delay(1000)
                    println("runBlocking --> 2nd GlobalScope.launch --> 1st launch --> launch --> after delay $it")
                }
            }
        }

        val job2 = launch {
            repeat(5) {
                println("runBlocking --> 2nd GlobalScope.launch --> 2nd launch --> before delay $it")
                delay(1000)
                println("runBlocking --> 2nd GlobalScope.launch --> 2nd launch --> after delay $it")
            }
            launch {
                repeat(5) {
                    println("runBlocking --> 2nd GlobalScope.launch --> 2nd launch --> launch --> before delay $it")
                    delay(1)
                    println("runBlocking --> 2nd GlobalScope.launch --> 2nd launch --> launch --> after delay $it")
                }
            }
        }
        println("runBlocking --> 2nd GlobalScope.launch --> before join")
        job1.join()
        job2.join()
        println("runBlocking --> 2nd GlobalScope.launch --> after join")
    }
    println("runBlocking --> before join")
    // Without this gJob1.join() and gJob2.join(), the program will exit before the `GlobalScope` coroutines finish.
    // It is something we have seen earlier:
    // We make the `runBlocking` to wait for the `GlobalScope.launch` execution using `join()`.
    // Reference: app/src/main/java/com/example/kotlincoroutines/snippets/basics/050jobJoin.kt
    gJob1.join()
    gJob2.join()
    println("runBlocking --> after join")
}