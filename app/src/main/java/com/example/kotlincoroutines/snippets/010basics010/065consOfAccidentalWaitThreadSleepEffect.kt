package com.example.kotlincoroutines.snippets.`010basics010`

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * # Problem with accidental wait For GlobalScope Execution: Thread.sleep:
 *
 * Earlier, we have seen
 * [Cons Of Accidental Wait](app/src/main/java/com/example/kotlincoroutines/snippets/basics/060consOfAccidentalWait.kt).
 *
 * We keep the program (`main` fun) busy in other work so that the `GlobalScope.launch` gets the chance of
 * execution.
 *
 * Here, we demonstrate that:
 *
 * The `GlobalScope` coroutine does not run on the `main` thread.
 * Hence, even if we block the main thread using `Thread.sleep`, it does not stop the `GlobalScope` to make some
 * progress.
 */
class ConsOfAccidentalWaitThreadSleep {
    init {
        println("ConsOfAccidentalWaitThreadSleep: Started init")
        // The `runBlocking` coroutine runs on the thread that calls it.
        // Here, we are calling `runBlocking` from the `main` thread.
        // Hence, the `runBlocking` coroutine runs on the `main` thread.
        runBlocking {
            println("ConsOfAccidentalWaitThreadSleep: 1st runBlocking on Thread: ${Thread.currentThread().name} --> GlobalScope.launch 01 and 02")
            GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWaitThreadSleep: 1st runBlocking -> 1st GlobalScope.launch Thread: " +
                                    "${Thread.currentThread().name} -> $it")
                    delay(100)
                }
            }
            GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWaitThreadSleep: 1st runBlocking -> 2nd GlobalScope.launch Thread: " +
                                    "${Thread.currentThread().name}-> $it")
                    delay(100)
                }
            }
            println("ConsOfAccidentalWaitThreadSleep: End of 1st runBlocking")
        }
        // This single line that sleeps the thread gives enough time to the `GlobalScope.launch` of the
        // first `runBlocking` to make more progress, not necessarily up to the completion point though.
        // This gives the `GlobalScopes` of the 1st `runBlocking` some dedicated time to make some progress without
        // competing with the `GlobalScopes` of the 2nd `runBlocking`.
        // Hence, in this case, the first `GlobalScopes` of the 1st `runBlocking` will print more, but still not all.
        // The thread that we block here using `Thread.sleep` is different than the thread that the `GlobalScopes`
        // uses from the shared thread-pool.
        // Hence, even when we block the main thread, it does not stop the `GlobalScope` to make some progress.
        // Normally, if we block the thread on which the coroutines is running, it also blocks the coroutine children.
        // However, `GlobalScope.launch` is an independent coroutine without any relation to the parent coroutine.
        // Hence, even though `GlobalScope.launch` is inside the `runBlocking`, and `runBlocking` is running on the
        // `main` thread, blocking the `main` thread does not stop the `GlobalScope.launch` to make some progress.
        println("ConsOfAccidentalWaitThreadSleep: Sleeping Thread: ${Thread.currentThread().name} ")
        Thread.sleep(1000) // Blocks only the `main` thread.
        runBlocking {
            println("ConsOfAccidentalWaitThreadSleep: 2nd runBlocking on Thread: ${Thread.currentThread().name} --> " +
                            " GlobalScope.launch 01 and 02 with join()")
            val job1 = GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWaitThreadSleep: 2nd runBlocking -> 1st GlobalScope.launch -> $it")
                    delay(10)
                }
            }

            val job2 = GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWaitThreadSleep: 2nd runBlocking -> 2nd GlobalScope.launch -> $it")
                    delay(10)
                }
            }

            println("ConsOfAccidentalWaitThreadSleep: Waiting for both coroutines to finish... job1.join() and job2.join()")
            job1.join()
            job2.join()
        }
    }
}

fun main() {
    ConsOfAccidentalWaitThreadSleep()
}