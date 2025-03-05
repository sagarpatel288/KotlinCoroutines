package com.example.kotlincoroutines.snippets.`020coroutineContexts020`

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CoroutineContextExample() {

    suspend fun accessCoroutineContext() {
        println("coroutineContext: $coroutineContext")
        val job = coroutineContext[Job]
        println("coroutineContext[Job]: $job")
        val name = coroutineContext[CoroutineName]
        println("coroutineContext[CoroutineName]: $name")
        val handler = coroutineContext[CoroutineExceptionHandler]
        println("coroutineContext[CoroutineExceptionHandler]: $handler")
    }

    fun accessCoroutineContextInScope() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("coroutineExceptionHandler: coroutineContext: $coroutineContext, throwable: $throwable")
        }
        val coroutineContext = Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler + CoroutineName("myCoroutineScope")
        val job = CoroutineScope(coroutineContext).launch {
                println("coroutineContext: $coroutineContext")
                val job = coroutineContext[Job]
                println("coroutineContext[Job]: $job")
                val name = coroutineContext[CoroutineName]
                println("coroutineContext[CoroutineName]: $name")
                val handler = coroutineContext[CoroutineExceptionHandler]
                println("coroutineContext[CoroutineExceptionHandler]: $handler")
            }
        // Cancels only the particular job.
        job.cancelChildren()
        // Cancels all the children coroutines that use the same scope - the scope that uses this coroutineContext.
        coroutineContext.cancelChildren()
        // Cancels the whole scope that uses this coroutineContext.
        coroutineContext.cancel()
    }
}