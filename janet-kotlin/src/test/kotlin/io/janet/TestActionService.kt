package io.janet

import io.janet.action.SimpleAction
import io.janet.action.TestAction

class TestActionService : ActionService() {
    override fun <A : Any> sendInternal(holder: ActionHolder<A>) {
        val action: TestAction = holder.action() as TestAction
        callback.onStart(holder)
        callback.onProgress(holder, 50)
        if (action.success) {
            callback.onSuccess(holder)
        } else {
            callback.onFail(holder, JanetException())
        }
    }

    override fun <A : Any> cancel(holder: ActionHolder<A>) {
    }

    override fun getSupportedAnnotationType(): Class<*> = SimpleAction::class.java

}
