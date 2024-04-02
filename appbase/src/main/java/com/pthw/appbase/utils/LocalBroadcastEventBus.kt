package com.pthw.appbase.utils

import com.pthw.appbase.utils.LocalBroadcastEventBus.EventType
import com.pthw.appbase.utils.LocalBroadcastEventBus.LocalEventListener
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

/**
 * Created by P.T.H.W on 20/11/2023.
 *
 * Replacement for [LocalBroadcastManager], it'll be deprecated in future. Inspire to EventBus,
 * use singleton [MutableStateFlow] and collected from [MainActivity]. Currently it's used for @401, @502, @RatingReview.
 * If you want to add more other events, add in [LocalEventListener], [EventType].
 */
class LocalBroadcastEventBus @Inject constructor() {
    private val _events = MutableStateFlow<EventType?>(null)
    private val events = _events.asStateFlow()

    fun publish(event: EventType) {
        Timber.i("EventBus : publish")
        _events.value = event
    }

    fun clear() {
        _events.value = null
    }

    suspend fun subscribe(onEvent: LocalEventListener) {
        clear()
        events.filterIsInstance<EventType>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                when (event) {
                    is EventType.UnAuthenticated -> {
                        onEvent.unauthenticated()
                    }

                    is EventType.ServiceUnavailable -> {
                        onEvent.serviceUnavailable()
                    }

                    is EventType.IllegalAccount -> {
                        onEvent.illegalAccount()
                    }

                    is EventType.NavigateScreen -> {
                        onEvent.navigateScreen(event.data)
                    }
                }
            }
    }

    sealed class EventType {
        object UnAuthenticated : EventType()
        object ServiceUnavailable : EventType()
        object IllegalAccount : EventType()
        class NavigateScreen(val data: Any?) : EventType()
    }

    abstract class LocalEventListener {
        // 401
        open fun unauthenticated() {
            Timber.i("EventBus : unauthenticated")
        }

        // 503
        open fun serviceUnavailable() {
            Timber.i("EventBus : serviceUnavailable")
        }

        // 451
        open fun illegalAccount() {
            Timber.i("EventBus : illegalAccount")
        }

        open fun navigateScreen(data: Any?) {
            Timber.i("EventBus : navigateScreen")
        }
    }
}