package com.example.kotlincoroutines.snippets.`020coroutineContexts020`

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The `Unconfined` dispatcher starts (continues) the execution from the current call-frame (from which it was called),
 * but resumes (finishes) on any thread that we might have launched in-between the unconfined block and is available.
 *
 * We normally don't use the unconfined dispatcher.
 * However, while writing the test, we use `unconfinedTestDispatcher`.
 *
 * We will learn about the `unconfinedTestDispatcher` later.
 *
 * [Kotlin Doc](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html)
 */
class UnconfinedExample {

    fun unconfinedExample() = runBlocking {
        // Prints main
        println("Thread: Inside runBlocking: ${Thread.currentThread().name}")
        withContext(Dispatchers.Unconfined) {
            // Prints main because we launched the unconfined dispatcher in the main thread.
            println("Thread: runBlocking -> unconfined: ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                // Prints default-worker.
                println("Thread: runBlocking -> unconfined -> IO -> ${Thread.currentThread().name}")
            }
            // Prints default-worker because we launched the `IO` dispatcher in-between.
            println("Thread: runBlocking -> Unconfined -> after the IO, within Unconfined: ${Thread.currentThread()
                .name}")
        }
    }
}

fun main() {
    val example = UnconfinedExample()
    example.unconfinedExample()
}