package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class AsyncAwait() {

    /**
     * Just like the `launch` builder, the `Async` builder also requires a scope or a parent coroutine.
     *
     * The `launch` builder is like fire and forget coroutine.
     * We don't care or read or process the result the background work might bring-in.
     *
     * When we want to get, read, or process the result, we use the `async` builder.
     * The `async` builder returns a `Deferred` object.
     * We can use the `await` method on the `Deferred` object to get the result.
     *
     * Async siblings without await give concurrency.
     * We use async siblings without await when we want to run the background work in parallel,
     * where none of the background work depends on the result of any other background work.
     */
    suspend fun asyncAwaitConcurrency() {
        coroutineScope {
            val deferred1 = async {
                println("asyncAwaitConcurrency: Some background work-1 started...")
                delay(2000L)
                println("asyncAwaitConcurrency: The background work-1 finished")
                "asyncAwaitConcurrency: Result-1 of Work-1"
            }
            val deferred2 = async {
                println("asyncAwaitConcurrency: Some background work-2 started...")
                delay(2000L)
                println("asyncAwaitConcurrency: The background work-2 finished")
                "asyncAwaitConcurrency: Result-2 of Work-2"
            }
            val time = measureTimeMillis {
                val result1 = deferred1.await()
                val result2 = deferred2.await()
                println("asyncAwaitConcurrency: result1 is $result1")
                println("asyncAwaitConcurrency: result2 is $result2")
            }
            println("asyncAwaitConcurrency: The total time taken is $time ms")
        }
    }

    /**
     * If we use `await` on each of the `async` coroutines before we start the new `async`,
     * then it will be a sequential execution.
     * The second `async` will start only after the first `async` finishes.
     *
     * We may do this when the second async is dependent on the result of the first async.
     */
    suspend fun asyncAwaitSequential() {
        coroutineScope {
            val time = measureTimeMillis {
                val result1 = async {
                    println("asyncAwaitSequential: Some background work-1 started...")
                    delay(2000L)
                    println("asyncAwaitSequential: The background work-1 finished")
                    "asyncAwaitSequential: Result-1 of Work-1"
                }.await()

                val result2 = async {
                    println("asyncAwaitSequential: Some background work-2 started...")
                    delay(2000L)
                    println("asyncAwaitSequential: The background work-2 finished")
                    "asyncAwaitSequential: Result-2 of Work-2"
                }.await()

                println("asyncAwaitSequential: result1 is $result1")
                println("asyncAwaitSequential: result2 is $result2")
            }
            println("asyncAwaitSequential: The total time taken is $time ms")
        }
    }
}

fun main() {
    val asyncAwait = AsyncAwait()
    runBlocking {
        asyncAwait.asyncAwaitConcurrency()
        asyncAwait.asyncAwaitSequential()
    }
}