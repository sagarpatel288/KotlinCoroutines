package com.example.kotlincoroutines.snippets.`010basics010`

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Concurrency() {

    /**
     * Siblings give concurrency. Siblings are co-operative tasks.
     * So, if we launch multiple coroutines, it gives concurrency.
     *
     * job1 and job2 are siblings and they give concurrency.
     */
    suspend fun concurrency() {
        coroutineScope {
            val job1 = launch {
                delay(2000L)
                println("concurreny --> GlobalScope.launch --> job1 launch --> ")
            }
            val job2 = launch {
                delay(2000L)
                println("concurreny --> GlobalScope.launch --> job2 launch --> ")
            }
            val jobTime = measureTimeMillis {
                job1.join()
                job2.join()
            }
            println("concurreny --> GlobalScope.launch --> both the jobs took --> $jobTime ms time")
        }
    }
}

class SequentialWaiting() {

    /**
     * However, a single coroutine block executes the code sequentially.
     * Within the single and same coroutine block,
     * the next instruction has to wait for the previous instruction to finish.
     */
    suspend fun sequentialWaiting() {
        coroutineScope {
            launch {
                val time = measureTimeMillis {
                    // All these instructions are executed sequentially as they are within the same coroutine block.
                    println("sequentialWaiting --> GlobalScope.launch --> delay 1 --> ")
                    delay(2000L)
                    println("sequentialWaiting --> GlobalScope.launch --> delay 1 finished --> ")
                    println("sequentialWaiting --> GlobalScope.launch --> delay 2 --> ")
                    delay(2000L)
                    println("sequentialWaiting --> GlobalScope.launch --> delay 2 finished --> ")
                }
                println("sequentialWaiting --> both the jobs took --> $time ms time")
            }
        }
    }
}

fun main() = runBlocking {
    val concurrency = Concurrency()
    concurrency.concurrency()
    val sequentialWaiting = SequentialWaiting()
    sequentialWaiting.sequentialWaiting()
}