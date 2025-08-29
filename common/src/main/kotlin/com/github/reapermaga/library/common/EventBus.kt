package com.github.reapermaga.library.common

import kotlin.reflect.KClass

/**
 * A simple event bus implementation.
 */
class EventBus {

    val subscribers: MutableMap<KClass<*>, MutableList<EventSubscription<*>>> = mutableMapOf()

    /**
     * Subscribe to an event type, with a priority.
     *
     * @param priority The priority of the subscriber. Lower values are called first.
     * @param subscriber The subscriber to call when the event is published.
     * @return The subscription object, which can be used to unsubscribe.
     */
    inline fun <reified E> subscribe(priority: Int = 100, subscriber: EventSubscriber<E>) =
        subscribers.getOrPut(E::class) { mutableListOf() }.let {
            val subscription = EventSubscription<E>(priority, subscriber)
            it.add(subscription)
            it.sortBy { it.priority }
            subscription
        }

    /**
     * Unsubscribe from an event type.
     *
     * @param subscription The subscription object to unsubscribe.
     * @return True if the subscription was found and removed, false otherwise.
     */
    inline fun <reified E> unsubscribe(subscription: EventSubscription<E>) =
        subscribers[E::class]?.remove(subscription) == true

    /**
     * Publish an event to all subscribers.
     *
     * @param event The event to publish.
     */
    inline fun <reified E> publish(event: E) =
        subscribers[E::class]?.forEach {
            @Suppress("UNCHECKED_CAST")
            (it as EventSubscription<E>).subscriber.onEvent(event)
        }
}

data class EventSubscription<E>(val priority: Int, val subscriber: EventSubscriber<E>)

fun interface EventSubscriber<E> {

    fun onEvent(event: E)

}

