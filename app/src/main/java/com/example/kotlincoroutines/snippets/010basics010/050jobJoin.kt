package com.example.kotlincoroutines.snippets.`010basics010`

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking

/**
 * # Accidental Wait For GlobalScope Execution: Using `job1.join()` and `job2.join()`.
 *
 * Earlier, we have seen
 * [Explicit Wait](app/src/main/java/com/example/kotlincoroutines/snippets/basics/030accidentalWaitForGlobalScope.kt)
 * where we have used `delay` to wait for the `GlobalScope.launch` execution.
 *
 * We have also seen
 * [Accidental wait using delay](app/src/main/java/com/example/kotlincoroutines/snippets/basics
 * /040accidentalWaitForGlobalScope.kt)
 *
 * We keep the program (`main` fun) busy in other work so that the `GlobalScope.launch` gets the chance of
 * execution.
 *
 * Here, we use `job1.join()` and `job2.join()` to wait for the `GlobalScope.launch` execution.
 *
 * This is more robust than using an arbitrary delay because:
 * It waits exactly as long as needed.
 * We're guaranteed to see all output.
 * No time is wasted waiting after the coroutines finish.
 */
class JobJoin {
    init {
        println("AccidentalWaitForGlobalScope: Started init")

        runBlocking {
            println("AccidentalWaitForGlobalScope: 1st runBlocking --> GlobalScope.launch 01 and 02")
            GlobalScope.launch { // This is NOT a child of runBlocking
                // work
                repeat(30) {
                    // This may or may not be printed until and unless we force the runBlocking to wait,
                    // or make the program busy so that the GlobalScope gets the chance of execution.
                    println("AccidentalWaitForGlobalScope: 1st runBlocking -> 1st GlobalScope.launch -> $it")
                }
            }
            GlobalScope.launch { // This is NOT a child of runBlocking
                // work
                repeat(30) {
                    // This may or may not be printed until and unless we force the runBlocking to wait,
                    // or make the program busy so that the GlobalScope gets the chance of execution.
                    println("AccidentalWaitForGlobalScope: 1st runBlocking -> 2nd GlobalScope.launch -> $it")
                }
            }
        } // runBlocking completes immediately without waiting

        runBlocking {
            println("AccidentalWaitForGlobalScope: 2nd runBlocking --> With job1().join() and job2.join()")
            // Store the jobs returned by GlobalScope.launch
            val job1 = GlobalScope.launch {
                repeat(30) {
                    println("AccidentalWaitForGlobalScope: 2nd runBlocking -> 1st GlobalScope.launch -> $it")
                }
            }

            val job2 = GlobalScope.launch {
                repeat(30) {
                    println("AccidentalWaitForGlobalScope: 2nd runBlocking -> 2nd GlobalScope.launch -> $it")
                }
            }

            // Properly wait for both coroutines to complete
            println("AccidentalWaitForGlobalScope: Waiting for both coroutines to finish... job1.join() and job2.join()")
            // We use join() to explicitly wait for these coroutines to complete.
            // This is more robust than using an arbitrary delay because:
            // It waits exactly as long as needed.
            // We're guaranteed to see all output.
            // No time is wasted waiting after the coroutines finish.
            job1.join()
            job2.join()
        }
    }
}

fun main() {
    JobJoin()
}