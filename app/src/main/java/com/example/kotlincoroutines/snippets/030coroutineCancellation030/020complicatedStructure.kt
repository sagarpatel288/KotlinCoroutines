package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComplicatedStructure {

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
            println("simpleExample: inside the outerJob: After the innerJob")
        }
        // This can be printed before any of the print statement inside the `innerJob` gets printed!
        println("simpleExample: Inside the runBlocking, after the nested scope!")
        outerJob.join() // job.join() waits here till the job completes.
        // This will be printed only after the `outerJob` completes.
        println("simpleExample: At the end of the simpleExample - Function end!")
    }

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

