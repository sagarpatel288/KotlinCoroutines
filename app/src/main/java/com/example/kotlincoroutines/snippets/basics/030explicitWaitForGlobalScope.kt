package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 *
 * # Forcing `runBlocking` to wait for the `GlobalScope.launch` execution
 *
 * A `GlobalScope.launch` is an independent coroutine without any relation to the parent coroutine.
 * Hence, when we use it inside `runBlocking`, the `runBlocking` does not have any idea about it.
 * So, the `runBlocking` scope will not wait for the `GlobalScope.launch` to finish.
 * Here, the `GlobalScope.launch` is like an orphan coroutine.
 * The program will exit before the `GlobalScope.launch` gets the chance of execution.
 *
 * The `GlobalScope.launch` will get the chance of execution only if the program is running.
 * We can make the `runBlocking` wait for the `GlobalScope.launch` execution by using `delay`, `join()`,
 * or by having enough `other work`.
 *
 * Here, we are explicitly waiting for the `GlobalScope.launch` to finish.
 * We force the `runBlocking` to wait by using `delay`.
 * Hence, the `GlobalScope.launch` will get the chance of execution.
 *
 * Once the `GlobalScope.launch` gets the chance of execution,
 * we will see the concurrency.
 * We will see that, the execution of both the `GlobalScope.launch` can be out-of-order.
 * We will see partial execution of one `GlobalScope.launch`, pause, and then start or resume the execution of the
 * other.
 * So, it quickly switches between the tasks.
 *
 * This is an example of the concurrency.
 *
 * # What is concurrency?
 *
 * [Reference](https://softwareengineering.stackexchange.com/a/366778/318803)
 * [Reference2](https://medium.com/@itIsMadhavan/concurrency-vs-parallelism-a-brief-review-b337c8dac350)
 *
 * An ability to run and deal with multiple tasks at the same time,
 * not necessarily simultaneously (not necessarily at exact same time).
 * So, concurrency is not necessarily parallelism. But, the structure or architecture can quickly switch
 * between the tasks.
 *
 * It means, the structure, or architecture can manage the multiple tasks out-of-order,
 * without affecting or changing the expected outcome.
 *
 * The structure or architecture does not necessarily pick up the tasks in order.
 *
 * For example, the structure or architecture can pick up the second task first,
 * pause the execution of the second task in middle, start the execution of the first task,
 * finish the execution of the first task, and then resume the execution of the second task.
 *
 * ## Analogy:
 *
 * When we, as a single resource, take a bite of a cake, and then sing for a while, again take another bite,
 * and sing for a while, we are doing concurrency.
 *
 * # What is parallelism?
 *
 * [Reference](https://softwareengineering.stackexchange.com/a/366778/318803)
 * [Reference2](https://medium.com/@itIsMadhavan/concurrency-vs-parallelism-a-brief-review-b337c8dac350)
 *
 * An ability to run and deal with multiple tasks at the exact same time, simultaneously.
 *
 * ## Analogy:
 *
 * When we get a partner, who sings and we eat the cake. We are doing parallelism.
 *
 * # Kotlin Coroutines
 *
 * Kotlin coroutines is a concurrency design pattern.
 *
 * If we run the below code (forget about the best practices for a while to understand the concept),
 * it will not have a pre-defined and fixed order of execution.
 * So, run it multiple times and see the output.
 */
class ExplicitWaitForGlobalScope() {
    init {
        println("ExplicitWait: Started init")
        runBlocking {
            println("ExplicitWait: runBlocking --> GlobalScope.launch 01")
            GlobalScope.launch {
                // work
                repeat(30) {
                    // This may or may not be printed until and unless we force the runBlocking to wait,
                    // or make the program busy so that the GlobalScope gets the chance of execution.
                    println("ExplicitWait: First Global Scope: runBlocking -> GlobalScope.launch -> $it")
                }
            }
            println("ExplicitWait: runBlocking --> GlobalScope.launch 02")
            GlobalScope.launch {
                // work
                repeat(30) {
                    // This may or may not be printed until and unless we force the runBlocking to wait,
                    // or make the program busy so that the GlobalScope gets the chance of execution.
                    println("ExplicitWait: Second Global Scope: runBlocking -> GlobalScope.launch -> $it")
                }
            }
            // We delay the runBlocking to wait for the GlobalScope.launch execution.
            // This is an explicit wait for the GlobalScope.launch execution.
            // However, this is not a robust solution because:
            // If the GlobalScope coroutines take longer than 1 second, they'll be cut off.
            // If they finish faster than 1 second, we're waiting unnecessarily.
            delay(1000)
        }
    }
}

fun main() {
    ExplicitWaitForGlobalScope()
}