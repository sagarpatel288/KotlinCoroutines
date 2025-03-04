package com.example.kotlincoroutines.snippets.coroutineContexts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * In this example, we see that the `Dispatchers.Default` is best suitable for the CPU intensive work,
 * and the `Dispatchers.IO` is best suitable for the background (io) work such as network call, database read/write,
 * file read/write, etc.
 *
 * Consider a specific relay running race where each time a member of the team hand-overs the stick to the other
 * member of the team, they both need to sign!
 *
 * In this situation, more the team members, more the time it will take to finish the race.
 * Because, switching the stick from one team member to another takes time!
 * Here, the team members are the coroutines and the stick is a thread (or the CPU resource).
 *
 * Now, consider a different situation.
 * Customer care team, disaster relief team, production team, etc.
 * In this case, more the team members, less the time it will take to finish the task.
 *
 * So, we learned that sometimes more people, more the time it takes to finish a task.
 * Whereas sometimes, more people, less the time it takes to finish a task.
 * It depends on the situation.
 *
 * Similarly, it is not always true that more threads means we finish a task faster.
 * Sometimes, more threads means we finish a task slower.
 * And sometimes, more threads means we finish a task faster.
 * It depends on the situation.
 *
 * We use the `Default` dispatcher for the CPU intensive work and the `IO` dispatcher for the background (io) work.
 *
 * The `Default` dispatcher is like a small team (CPU Cores) of relay runners (high performance threads),
 * where we cannot afford frequent context switching. We need less interruptions.
 *
 * The `IO` dispatcher is like a customer care team, disaster relief team, or a production team,
 * where we cannot afford waiting time (a large pending request queue). We need more resources.
 *
 * If we use the `Default` dispatcher for the blocking work,
 * it means that we use a small team (CPU Cores) to handle and manage customer care service, disaster relief work,
 * or production work. It takes more time.
 *
 * Hence, we should use the `Default` dispatcher for the CPU intensive work only. It is made for it.
 *
 * If we use the `IO` dispatcher for the non-blocking work,
 * it means that we use a large team (around 64 threads) in a relay race where each time a member of the team
 * hand-overs the stick (CPU resource) to the other member of the team, they both need to sign! It takes more time.
 *
 * Hence, we should use the `IO` dispatcher for the background (io) work only. It is made for it.
 * So that we can handle more blocking requests (network call, database read/write, file read/write, etc.).
 *
 * Analogy:
 *
 * Imagine a situation where we need to fill many small water bottles for a football stadium, or for a marathon race.
 * Vs. a situation where we need to fill a small amount of large swimming pools.
 *
 * The large number of small containers to fill many water bottles represents the `IO` dispatcher,
 * whereas the small number of large containers to fill a few swimming pools represents the `Default` dispatcher.
 *
 * It is like, the less number of large containers (CPU Cores) Vs. the more number of small containers (IO
 * Thread-Pool).
 *
 * The `Default` dispatcher is like a limited supply (equals to the CPU Cores),
 * and we use it for the tasks that require high performance (CPU intensive work).
 * If we use the `Default` dispatcher where the demand is high, than we run out of the supply.
 *
 * If we use the `Default` dispatcher for a blocking operation like a network call, file read/write,
 * database read/write, etc., then we quickly run out of the supply.
 * That is to say, all the available (and limited) threads (equals to the CPU Cores) will be occupied quickly,
 * making other tasks (demand) wait until the current task is completed and one of the resources (thread) becomes
 * available.
 *
 * During a blocking operation such as a network call, file read/write, database read/write, etc.,
 * the thread has to wait for the operation to complete.
 * The thread is occupied and cannot be used for other tasks until the operation is completed, and
 * it includes the waiting time.
 *
 * That's why, we use the `IO` dispatcher for a blocking operation.
 * So that, we get more resources (threads) to handle more tasks.
 *
 * A CPU is a limited resource that threads share.
 * When there are more threads, the system has to perform more context switching between threads to give each thread
 * a fair chance to run.
 *
 * Each context switching includes an additional task of saving the work (state) of the current thread,
 * and loading the work (state) of the next thread.
 *
 * Now, if it is not a blocking operation and there are more threads than the CPU cores,
 * the system performs more context switching, and it leads to a situation where a thread gets more interruptions
 * before it can complete the task, which ultimately increases the time for each thread to finish their tasks.
 *
 * In such a situation, the system spends more time in context switching than in performing and completing the
 * actual computational task.
 *
 * When we use the `Default` dispatcher for the CPU intensive work, there is no contention for the CPU,
 * because each thread gets its own core. We get minimum context switching and the CPU can focus on performing
 * computations without being interrupted frequently, which is why the `Default` dispatcher completes the CPU intensive
 * work faster than the `IO` dispatcher.
 *
 */
class IoVsDefault {

    fun defaultIsSlowForBlockingOperations() = runBlocking {
        val threads = hashMapOf<Long, String>()
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(100) {
                launch {
                    threads[Thread.currentThread().id] = Thread.currentThread().name
                    // Simulates a Blocking operation
                    Thread.sleep(1000L)
                }
            }
        }
        val time = measureTimeMillis {
            job.join()
        }
        println("Background work: Default launched ${threads.keys.size} threads and finished in $time ms")
    }

    fun ioIsFastForBlockingOperations() = runBlocking {
        val threads = hashMapOf<Long, String>()
        val job = GlobalScope.launch(Dispatchers.IO) {
            repeat(100) {
                launch {
                    threads[Thread.currentThread().id] = Thread.currentThread().name
                    // Simulates a Blocking operation
                    Thread.sleep(1000L)
                }
            }
        }
        val time = measureTimeMillis {
            job.join()
        }
        println("Background work: IO thread launched ${threads.keys.size} threads and finished in $time ms")
    }

    fun defaultIsFastForIntensiveComputation() = runBlocking {
        val threads = hashMapOf<Long, String>()
        val job1 = GlobalScope.launch(Dispatchers.Default) {
            repeat(100) {
                launch {
                    threads[Thread.currentThread().id] = Thread.currentThread().name
                    // Simulates a CPU intensive work
                    (1..100000).map {
                        it * it
                    }
                }
            }
        }
        val time = measureTimeMillis {
            job1.join()
        }
        println("CPU Intensive work: Default thread launched ${threads.keys.size} threads and finished in $time ms")
    }

    fun ioIsSlowForIntensiveComputation() = runBlocking {
        val threads = hashMapOf<Long, String>()
        val job1 = GlobalScope.launch(Dispatchers.IO) {
            repeat(100) {
                launch {
                    threads[Thread.currentThread().id] = Thread.currentThread().name
                    // Simulates a CPU intensive work
                    (1..100000).map {
                        it * it
                    }
                }
            }
        }
        val time = measureTimeMillis {
            job1.join()
        }
        println("CPU Intensive work: IO launched ${threads.keys.size} and finished in $time ms")
    }
}

fun main() {
    IoVsDefault().defaultIsSlowForBlockingOperations()
    IoVsDefault().ioIsFastForBlockingOperations()
    IoVsDefault().defaultIsFastForIntensiveComputation()
    IoVsDefault().ioIsSlowForIntensiveComputation()
}