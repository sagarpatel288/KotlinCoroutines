package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * # What is Coroutines?
 *
 * ## Google Android Official Definition:
 *
 * [Google Android Official Definition](https://developer.android.com/kotlin/coroutines)
 *
 * **_A coroutine is a concurrency design pattern_** that you can use on Android to simplify code,
 * **_that executes asynchronously_**.
 *
 * Coroutines were added to Kotlin in version 1.3 and are based on established concepts from other languages.
 * On Android, coroutines help to **_manage long-running tasks that might otherwise block the main thread_** and
 * cause your app to become unresponsive.
 *
 * Over 50% of professional developers who use coroutines have reported seeing increased productivity.
 * This topic describes how you can use Kotlin coroutines to address these problems,
 * enabling you to write cleaner and more concise app code.
 *
 * ## Jetbrains Official Definition:
 *
 * [Jetbrains Official Definition](https://kotlinlang.org/docs/coroutines-basics.html#your-first-coroutine)
 *
 * A coroutine is an **_instance of a suspendable computation_**.
 * It is conceptually similar to a thread, in the sense that it takes a block of code to run that
 * **_works concurrently_** with the rest of the code.
 *
 * However, a coroutine is not bound to any particular thread.
 * It may suspend its execution in one thread and resume in another one.
 *
 * Coroutines can be thought of as **_light-weight threads_**, but there are a number of important
 * differences that make their real-life usage very different from threads.
 *
 * ----------------------------------------------
 *
 * The `main` function is the entry point of the application.
 */
fun main() = runBlocking {
    println("Before the suspension point")
    // The `delay` function suspends the coroutine for 3 seconds.
    delay(3000) // The `delay` function is a suspendable function, a suspension point.
    // The below line will be executed after the delay of 3 seconds.
    println("After the suspension point")
}