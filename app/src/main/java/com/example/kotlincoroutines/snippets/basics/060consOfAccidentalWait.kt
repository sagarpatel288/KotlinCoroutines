package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * # Problem with accidental wait For GlobalScope Execution:
 *
 * Earlier, we have seen
 * [Explicit Wait](app/src/main/java/com/example/kotlincoroutines/snippets/basics/030explicitWaitForGlobalScope.kt)
 * where we have used `delay` to wait for the `GlobalScope.launch` execution.
 *
 * We have also seen
 * [Accidental wait using delay](app/src/main/java/com/example/kotlincoroutines/snippets/basics
 * /040accidentalWaitForGlobalScope.kt)
 *
 * and
 * [Wait using job.join](app/src/main/java/com/example/kotlincoroutines/snippets/basics/050jobJoin.kt)
 *
 * We keep the program (`main` fun) busy in other work so that the `GlobalScope.launch` gets the chance of
 * execution.
 *
 * Here, we demonstrate that:
 *
 * The GlobalScope coroutines from the first block might not complete their printing.
 * This is because there's no guarantee they'll have enough time before the program moves on (finishes).
 * When the second `runBlocking` finishes the job, it does not care about the orphan `GlobalScope` coroutines
 * of the first `runBlocking`.
 */
class ConsOfAccidentalWait {
    init {
        println("ConsOfAccidentalWait: Started init")

        runBlocking {
            println("ConsOfAccidentalWait: 1st runBlocking --> GlobalScope.launch 01 and 02")
            GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWait: 1st runBlocking -> 1st GlobalScope.launch -> $it")
                    delay(100)
                }
            }
            GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWait: 1st runBlocking -> 2nd GlobalScope.launch -> $it")
                    delay(100)
                }
            }
            println("ConsOfAccidentalWait: End of 1st runBlocking")
        }

        runBlocking {
            println("ConsOfAccidentalWait: 2nd runBlocking --> GlobalScope.launch 01 and 02 with join()")
            val job1 = GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWait: 2nd runBlocking -> 1st GlobalScope.launch -> $it")
                    delay(10)
                }
            }

            val job2 = GlobalScope.launch {
                repeat(30) {
                    println("ConsOfAccidentalWait: 2nd runBlocking -> 2nd GlobalScope.launch -> $it")
                    delay(10)
                }
            }

            println("ConsOfAccidentalWait: Waiting for both coroutines to finish... job1.join() and job2.join()")
            job1.join()
            job2.join()
        }
    }
}

fun main() {
    ConsOfAccidentalWait()
}