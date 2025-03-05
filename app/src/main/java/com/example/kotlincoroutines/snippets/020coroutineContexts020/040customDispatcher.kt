package com.example.kotlincoroutines.snippets.`020coroutineContexts020`

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

/**
 * We can create our own custom dispatcher by inheriting `CoroutineDispatcher()`.
 * We need to override the `dispatch()` method. It is mandatory.
 * Other methods are optional.
 */
class CustomDispatcher: CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        TODO("Not yet implemented")
    }
}

