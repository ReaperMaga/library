package com.github.reapermaga.library.common.concurrency

/**
 * A class that allows to execute operations with a delay and debounce them in a virtual thread.
 * The operations are identified by a unique id. If an operation with the same id is added while the previous one is still running, the new operation will be ignored.
 *
 * @param delay The delay in milliseconds to wait before executing the operation.
 * @param execute The operation to execute.
 */
class DebouncedOperationExecutor<T>(val delay : Long, val execute : (T) -> Unit) {

    private val runningOperations = mutableSetOf<String>()

    /**
     * Adds an operation to the executor. If an operation with the same id is already running, the new operation will be ignored.
     *
     * @param id The unique id of the operation.
     * @param operation The operation to execute
     */
    fun addOperation(id : String, operation : T) {
        if (isOperationRunning(id)) {
            return
        }
        runningOperations.add(id)
        Thread.ofVirtual().start {
            Thread.sleep(delay)
            runningOperations.remove(id)
            execute(operation)
        }
    }

    /**
     * Checks if an operation with the given id is currently running.
     *
     * @param id The id of the operation.
     * @return True if the operation is running, false otherwise.
     */
    fun isOperationRunning(id : String) = runningOperations.contains(id)
}