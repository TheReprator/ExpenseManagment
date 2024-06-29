package dev.reprator.core.util

fun interface Mapper<in From, out To> {
    fun map(from: From): To
}

inline fun <F, T> Mapper<F, T>.mapAll(collection: Collection<F>) = collection.map { map(it) }