package com.github.reapermaga.library.common.concurrency

/**
 * Distributes tasks to workers in a long live manner. Workers are created on initialization and are kept alive until shutdown.
 * Tasks are distributed to workers in a round-robin manner.
 *
 * @param numWorkers Number of workers to create
 * @param delayBetweenIteration Delay between each iteration of the worker. This is useful to prevent the worker from consuming too much CPU.
 * @param runner The callback that will run the tasks
 */
class LongLiveTaskDistributor<T>(
    var numWorkers: Int,
    val delayBetweenIteration: Long = 1000,
    val runner: TaskRunner<T>,
) {
    private val workers = mutableListOf<TaskDistributorWorker<T>>()

    init {
        createWorkers()
    }

    /**
     * Adds a task to the worker with the least amount of tasks
     */
    fun addTask(task: T) {
        val worker = workers.minByOrNull { it.tasks.size } ?: return
        worker.tasks.add(task)
    }

    /**
     * Scales the number of workers. This will stop all workers and recreate them with the new number of workers.
     * All current tasks will be redistributed to the new workers.
     *
     * @param numWorkers The new number of workers
     */
    fun scale(numWorkers: Int) {
        this.numWorkers = numWorkers
        stopWorkers()
        val tasks = workers.flatMap { it.tasks }
        workers.clear()
        createWorkers()
        tasks.forEach { addTask(it) }
    }

    /**
     * Stops all workers
     */
    fun shutdown() {
        workers.forEach {
            it.close()
        }
    }

    private fun createWorkers() {
        for (i in 0 until numWorkers) {
            val worker = TaskDistributorWorker<T>(delayBetweenIteration, runner)
            workers.add(worker)
        }
    }

    private fun stopWorkers() {
        workers.forEach {
            it.close()
        }
    }
}

fun interface TaskRunner<T> {
    fun run(task: T)
}

private class TaskDistributorWorker<T>(
    val delayBetweenIteration: Long,
    val runner: TaskRunner<T>,
) : Thread() {
    val tasks = mutableListOf<T>()
    var isRunning = true

    init {
        start()
    }

    override fun run() {
        while (isRunning) {
            if (delayBetweenIteration > 0) {
                sleep(delayBetweenIteration)
            }
            tasks.forEach {
                runner.run(it)
            }
        }
    }

    fun close() {
        isRunning = false
    }
}
