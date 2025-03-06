package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComplicatedStructure {

    /**
     * This function demonstrates the nested scopes and coroutines.
     *
     * The print statements inside the function will not be printed sequentially,
     * because we launch multiple coroutines and coroutines are concurrent by nature (by design).
     *
     * Each coroutine is an independent and asynchronous (concurrent) entity.
     *
     * In a lexical and nested coroutines where we launch multiple coroutines inside and outside each-other,
     * the outer (parent) coroutine continues its execution even when it faces any internal, inner (child) coroutine,
     * and the outer (parent) coroutine may complete its block before the internal or inner (child) coroutine can
     * complete its own block.
     *
     * The outer (parent) coroutine does not wait for the inner (children) coroutines unless explicitly synchronized
     * using `job.join()` or `await()`.
     *
     * However, this is not true when it comes to a suspend function where the instructions are executed sequentially
     * until and unless we hit a nested, internal, inner coroutine.
     */
    fun nestedScopesAndCoroutines() = runBlocking {
        println("simpleExample: Launching a nested scope!")
        val outerJob = CoroutineScope(Dispatchers.Default).launch {
            println("simpleExample: Inside the outerJob: Launching inner job")
            val innerJob = launch {
                println("simpleExample: InnerJob: Calling the suspendFunction!")
                suspendFunction()
                println("simpleExample: InnerJob: Finished the suspendFunction!")
            }
            // This can be printed before the innerJob finishes!
            // Because, we do not explicitly wait here till the `innerJob` completes. E.g., using `job.join()`.
            println("simpleExample: inside the outerJob: After the innerJob")
        }
        // This can be printed before any of the print statement inside the `innerJob` gets printed!
        println("simpleExample: Inside the runBlocking, after the nested scope!")
        // job.join() waits here till the job completes.
        outerJob.join() // Without this, the `runBlocking` may complete without waiting for children coroutines.
        // This will be printed only after the `outerJob` completes, because we explicitly wait by `outerJob.join()`.
        println("simpleExample: At the end of the simpleExample - Function end!")
    }

    /**
     * Inside a suspend function,
     * the instructions are executed sequentially.
     * Each `withContext` block runs synchronously (sequentially), because the `withContext` block acts as `job.join()`,
     * suspending the coroutine until its block completes.
     *
     * The entire suspend function is executed within a single coroutine,
     * and one `withContext` block must complete before the next `withContext` block begins,
     * the print statements will appear sequentially.
     *
     * If we launch a nested, internal, inner, lexical coroutine inside the suspend function,
     * it will introduce concurrency, because coroutines are concurrent by nature (by design).
     */
    private suspend fun suspendFunction() {
        println("suspendFunction: Launching IO")
        withContext(Dispatchers.IO) {
            println("suspendFunction: Inside IO: Going to sleep!")
            Thread.sleep(2000L)
            println("suspendFunction: Inside IO: Finished sleeping!")
        }
        println("suspendFunction: Launching Default")
        withContext(Dispatchers.Default) {
            println("suspendFunction: Inside the Default")
            repeat(100) {
                (1..10).map {
                    it * it
                }
            }
            println("suspendFunction: Finished the CPU Intensive work!")
        }
        println("suspendFunction: After both the withContext, at the end of the suspend function!")
    }
}

fun main() {
    ComplicatedStructure().nestedScopesAndCoroutines()
}

