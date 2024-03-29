package kr.es6.kotloid

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

/**
 * Experimental delegate to have weak properties without boilerplate.
 *
 * See [weakRef] for usage.
 */
class WeakRefHolder<T>(private var initializer: () -> T) {
    private var _value: WeakReference<T?>? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _value?.get() ?: initializer().also { _value = WeakReference(it) }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _value = WeakReference(value)
    }
}

/**
 * Use the `by` keyword to delegate weak references.
 *
 * Internally this creates a [WeakRefHolder] that will store the real
 * [WeakReference]. Example usage:
 *
 *     var weakContext: Context? by weakRef(null)
 *     …
 *     weakContext = strongContext
 *     …
 *     context = weakContext
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> weakRef(value: T?) = WeakRefHolder { WeakReference(value) }

/**
 * Use the `by` keyword to delegate weak references.
 *
 * Internally this creates a [WeakRefHolder] that will store the real
 * [WeakReference].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> weakRef(noinline initializer: () -> T) = WeakRefHolder(initializer)
