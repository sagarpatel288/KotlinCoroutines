package com.example.kotlincoroutines.snippets.`010basics010`

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking

/**
 * A `GlobalScope.launch` is an independent coroutine without any relation to the parent coroutine.
 * Hence, when we use it inside `runBlocking`, the `runBlocking` does not have any idea about it.
 * So, the `runBlocking` scope will not wait for the `GlobalScope.launch` to finish.
 * Here, the `GlobalScope.launch` is like an orphan coroutine.
 * The program will exit before the `GlobalScope.launch` gets the chance of execution.
 *
 * The `GlobalScope.launch` will get the chance of execution only if the program is running.
 * We can make the `runBlocking` wait for the `GlobalScope.launch` to finish by using `delay`, `join()`,
 * or by having enough `other work`.
 */
class OrphanGlobalScope {
    init {
        println("OrphanGlobalScope: Started init")
    }

    fun orphanGlobalScope() {
        runBlocking {
            println("OrphanGlobalScope: started runBlocking --> ")
            GlobalScope.launch { // This is NOT a child of runBlocking
                // work
                repeat(30) {
                    // This will not be printed until and unless we force the runBlocking to wait,
                    // or make the program busy so that the GlobalScope gets the chance of execution.
                    println("OrphanGlobalScope: First Global Scope: runBlocking -> GlobalScope.launch -> $it")
                }
            }
            println("Finished runBlocking")
        } // runBlocking completes immediately without waiting.
    }
}

fun main() {
    OrphanGlobalScope().orphanGlobalScope()
}