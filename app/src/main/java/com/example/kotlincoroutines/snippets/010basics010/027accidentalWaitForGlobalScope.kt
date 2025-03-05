package com.example.kotlincoroutines.snippets.`010basics010`

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

/**
 * This may print the statements of each GlobalScope,
 * because we do not finish the execution of the `main`.
 * We have put a `delay` before we finish the `main` execution.
 * It means, the `GlobalScope` may get a chance for the execution.
 */
suspend fun main() {
    val job1 = GlobalScope.launch {
        println("Inside the 1st Global Scope - 01")
    }
    val job2 = GlobalScope.launch {
        println("Inside the 2nd Global Scope - 02")
    }
    GlobalScope.launch {
        job1.join()
        job2.join()
    }
    delay(2000L)
}