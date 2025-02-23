package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CoroutinePractice() {

    /**
     * In this example, we create a coroutine scope and a job inside it.
     * We demonstrate how to cancel the job and check its status.
     * We also demonstrate that the coroutine scope is still active after canceling the job,
     * because it lives as long as the application is running.
     *
     * We have to manually cancel the coroutine scope.
     */
    suspend fun coroutinePractice() {
        val coroutineScope = CoroutineScope(Dispatchers.IO + Job() + CoroutineName("myCoroutineScope"))
        val job = coroutineScope.launch {
            println("inside the coroutineScope")
        }
        println("Is the job active? ${job.isActive}")
        println("Cancelling the job...")
        delay(6000L)
        job.cancel()
        println("Is the job active? ${job.isActive}")
        println("The coroutine scope is active: ${coroutineScope.coroutineContext.isActive}")
        println("Cancelling the coroutine scope")
        delay(3000L)
        coroutineScope.coroutineContext.cancel()
        println("The coroutine scope is active: ${coroutineScope.coroutineContext.isActive}")
        val job2 = coroutineScope.launch {
            println("inside the coroutineScope 2")
        }
        println("Is the job active? ${job2.isActive}")
        println("Is the coroutine scope active? ${coroutineScope.coroutineContext.isActive}")
    }
}

fun main() {
    runBlocking {
        CoroutinePractice().coroutinePractice()
    }
}

