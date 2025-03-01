package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

/**
 * # Accidental Wait For GlobalScope Execution: Using `delay`.
 *
 * Earlier, we have seen
 * [Explicit Wait](app/src/main/java/com/example/kotlincoroutines/snippets/basics/030accidentalWaitForGlobalScope.kt)
 * where we have used `delay` to wait for the `GlobalScope.launch` execution.
 *
 * Here, we keep the program (`main` fun) busy in other work so that the `GlobalScope.launch` gets the chance of
 * execution.
 *
 * We use an arbitrary delay to wait for the `GlobalScope.launch` execution.
 *
 * However, this is not a robust solution because:
 * If the GlobalScope coroutines take longer than the specified delay, they'll be cut off.
 * If they finish faster than the specified delay, we're waiting unnecessarily.
 */
class AccidentalWaitForGlobalScope {
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
            println("AccidentalWaitForGlobalScope: 2nd runBlocking --> With delay to force the runBlocking wait.")
            GlobalScope.launch {
                repeat(30) {
                    println("AccidentalWaitForGlobalScope: 2nd runBlocking -> 1st GlobalScope.launch -> $it ")
                }
            }

            GlobalScope.launch {
                repeat(30) {
                    println("AccidentalWaitForGlobalScope: 2nd runBlocking -> 2nd GlobalScope.launch -> $it")
                }
            }

            // Properly wait for both coroutines to complete
            println("AccidentalWaitForGlobalScope: Making (forcing) the program to wait")
            // We delay the 2nd runBlocking to wait for the GlobalScope.launch execution.
            // This is an explicit wait for the GlobalScope.launch execution.
            // However, this is not a robust solution because:
            // If the GlobalScope coroutines take longer than the specified delay, they'll be cut off.
            // If they finish faster than the specified delay, we're waiting unnecessarily.
            delay(1000)
        }
    }
}

fun main() {
    AccidentalWaitForGlobalScope()
}