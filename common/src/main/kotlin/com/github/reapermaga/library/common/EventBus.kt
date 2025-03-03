package com.github.reapermaga.library.common

import kotlin.reflect.KClass

class EventBus {

    val subscribers : MutableMap<KClass<*>, MutableList<EventSubscription<*>>> = mutableMapOf()

    inline fun <reified E> subscribe(priority : Int = 100, subscriber : EventSubscriber<E>) =
        subscribers.getOrPut(E::class) { mutableListOf() }.let {
            val subscription = EventSubscription<E>(priority, subscriber)
            it.add(subscription)
            it.sortBy { it.priority }
            subscription
        }


    inline fun <reified E> publish(event : E) =
        subscribers[E::class]?.forEach {
            @Suppress("UNCHECKED_CAST")
            (it as EventSubscription<E>).subscriber.onEvent(event)
        }
}

data class EventSubscription<E>(val priority : Int, val subscriber : EventSubscriber<E>)

fun interface EventSubscriber<E> {

    fun onEvent(event : E)

}

