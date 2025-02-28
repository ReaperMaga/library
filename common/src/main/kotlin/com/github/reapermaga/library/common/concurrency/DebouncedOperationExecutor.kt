package com.github.reapermaga.library.common.concurrency

class DebouncedOperationExecutor<T>(val delay : Long, val execute : (T) -> Unit) {

    private val runningOperations = mutableSetOf<String>()

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

    fun isOperationRunning(id : String) = runningOperations.contains(id)
}