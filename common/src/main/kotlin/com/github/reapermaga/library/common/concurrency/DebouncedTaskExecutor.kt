package com.github.reapermaga.library.common.concurrency

import java.util.concurrent.CompletableFuture

/**
 * A class that allows to execute tasks with a delay and debounce them in a virtual thread.
 * The tasks are identified by a unique id. If an task with the same id is added while the previous one is still running, the new task will be ignored.
 *
 * @param delay The delay in milliseconds to wait before executing the task.
 * @param execute The task to execute.
 */
class DebouncedTaskExecutor<T>(
    val delay: Long,
    val execute: (T) -> Unit,
) {
    private val runningTasks = mutableSetOf<String>()

    /**
     * Adds an task to the executor. If a task with the same id is already running, the new task will be ignored.
     *
     * @param id The unique id of the task.
     * @param task The task to execute
     */
    fun addTask(
        id: String,
        task: T,
    ) {
        if (isTaskRunning(id)) {
            return
        }
        runningTasks.add(id)
        Thread.ofVirtual().start {
            Thread.sleep(delay)
            runningTasks.remove(id)
            execute(task)
        }
    }

    /**
     * Adds an task to the executor and returns a CompletableFuture that will be completed when the task is finished.
     * If a task with the same id is already running, the new task will be ignored.
     *
     * @param id The unique id of the task.
     * @param task The task to execute
     * @return A CompletableFuture that will be completed when the task is finished. Null if the task is already running.
     */
    fun addTaskWithFuture(
        id: String,
        task: T,
    ): CompletableFuture<T>? {
        if (isTaskRunning(id)) {
            return null
        }
        val future = CompletableFuture<T>()
        runningTasks.add(id)
        Thread.ofVirtual().start {
            Thread.sleep(delay)
            runningTasks.remove(id)
            execute(task)
            future.complete(task)
        }
        return future
    }

    /**
     * Checks if a task with the given id is currently running.
     *
     * @param id The id of the task.
     * @return True if the task is running, false otherwise.
     */
    fun isTaskRunning(id: String) = runningTasks.contains(id)
}
