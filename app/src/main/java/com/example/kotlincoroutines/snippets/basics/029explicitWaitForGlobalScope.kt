package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.GlobalScope

/**
 * This will also print the statements of each `GlobalScope`,
 * because we are in the `runBlocking` and we explicitly say `job1.join()`, and `job2.join()`, before we close the
 * `runBlocking`.
 */
fun main() = runBlocking {
    val job1 = GlobalScope.launch {
        println("Inside the 1st Global Scope - 01")
    }
    val job2 = GlobalScope.launch {
        println("Inside the 2nd Global Scope - 02")
    }
    // job.join() directly inside the `runBlocking` ensures that,
    // the `runBlocking` waits for these jobs to finish their executions.
    job1.join()
    job2.join()
}