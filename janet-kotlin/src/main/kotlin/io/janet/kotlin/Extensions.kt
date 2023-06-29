package io.janet.kotlin

import io.janet.ActionPipe
import io.janet.ActionService
import io.janet.ActionState
import io.janet.Janet
import io.janet.helper.ActionStateSubscriber
import io.janet.helper.ActionStateToActionTransformer
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

fun janet(evaluateBody: Janet.Builder.() -> Unit): Janet {
    val builder = Janet.Builder()
    builder.evaluateBody()
    return builder.build()
}

fun janet(vararg services: ActionService): Janet {
    return services.zip(arrayOf(Janet.Builder())) { t1, t2 ->
        t2.addService(t1)
    }.single().build()
}

fun ActionService.wrap(evaluateBody: ActionServiceWrapperBody.() -> Unit): ActionServiceWrapper {
    return ActionServiceWrapper(this) {
        evaluateBody()
    }
}

inline fun <reified A : Any> Janet.createPipe(): ActionPipe<A> {
    return createPipe(A::class.java)
}

inline fun <reified A : Any> Janet.createPipe(scheduler: Scheduler): ActionPipe<A> {
    return createPipe(A::class.java, scheduler)
}

inline fun <reified A : Any> Flowable<ActionState<A>>.subscribeAction(evaluateBody: ActionStateSubscriber<A>.() -> Unit): Disposable {
    val subscriber = ActionStateSubscriber<A>()
    subscriber.evaluateBody()
    subscribe(subscriber)
    return subscriber
}

inline fun <reified A : Any> Flowable<ActionState<A>>.mapToResult(): Flowable<A> {
    return compose(ActionStateToActionTransformer())
}

inline fun <reified A : Any> Flowable<ActionState<A>>.takeUntil(vararg statuses: ActionState.Status): Flowable<ActionState<A>> {
    return takeUntil { statuses.contains(it.status) }
}

//TODO: add javadoc for each method





