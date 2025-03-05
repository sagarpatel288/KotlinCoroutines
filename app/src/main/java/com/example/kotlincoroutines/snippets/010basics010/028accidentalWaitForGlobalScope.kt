package com.example.kotlincoroutines.snippets.`010basics010`

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlin.system.measureTimeMillis

/**
 * We put a `delay` before we finish the `runBlocking`.
 * Hence, the `GlobalScope` may get a chance for the execution.
 *
 * If you reduce the `delay` to 1ms instead of 1000ms,
 * maybe one of the `GlobalScope` will not get the chance for the execution.
 *
 * You can play with this example.
 * Observe, how much time it takes for both the `GlobalScope` execution.
 * And then, reduce (decrease) the `delay` than the execution time of both the `GlobalScope`.
 * It may prevent one of the `GlobalScope` execution,
 * because the `runBlocking` will end (finish) earlier before the `GlobalScope` gets a chance for the execution.
 */
fun main() = runBlocking {
    val job1 = GlobalScope.launch {
        println("Inside the 1st Global Scope - 01")
    }
    val job2 = GlobalScope.launch {
        println("Inside the 2nd Global Scope - 02")
    }
    GlobalScope.launch {
        val time = measureTimeMillis {
            job1.join()
            job2.join()
        }
        println("Both Global Scope execution finished after $time ms")
    }
    delay(1000L)
}