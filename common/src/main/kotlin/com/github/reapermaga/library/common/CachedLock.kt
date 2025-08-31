package com.github.reapermaga.library.common

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

/**
 * A utility object that provides cached locking mechanisms using coroutines Mutex.
 * Locks are cached based on a string key and expire after a period of inactivity.
 *
 * Note: This implementation uses Guava's Cache for caching Mutex instances.
 *       Make sure to include Guava in your project dependencies.
 */
object CachedLock {
    private val mutexCache =
        CacheBuilder
            .newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build<String, Mutex>(
                CacheLoader.from { _ ->
                    Mutex()
                },
            )

    /**
     * Attempts to acquire a lock for the given key and execute the provided suspending function within the specified timeout.
     * If the lock cannot be acquired immediately, it returns null.
     *
     * @param key The key to identify the lock.
     * @param timeout The maximum time in milliseconds to wait for the operation to complete.
     * @param run The suspending function to execute while holding the lock.
     * @return The result of the suspending function if the lock was acquired, or null otherwise.
     */
    suspend fun <T> tryLockWithTimeout(
        key: String,
        timeout: Long,
        run: suspend () -> T,
    ): T? {
        val mutex = mutexCache.get(key)
        if (mutex.tryLock()) {
            try {
                withTimeout(timeout) {
                    run()
                }
            } finally {
                mutex.unlock()
            }
        }
        return null
    }

    /**
     * Acquires a lock for the given key and executes the provided suspending function within the specified timeout.
     * This function will suspend until the lock is acquired.
     *
     * @param key The key to identify the lock.
     * @param timeout The maximum time in milliseconds to wait for the operation to complete.
     * @param run The suspending function to execute while holding the lock.
     * @return The result of the suspending function.
     */
    suspend fun <T> lockWithTimeout(
        key: String,
        timeout: Long = 3000,
        run: suspend () -> T,
    ): T {
        val mutex = mutexCache.get(key)
        mutex.lock()
        try {
            return withTimeout(timeout) {
                run()
            }
        } finally {
            mutex.unlock()
        }
    }
}
