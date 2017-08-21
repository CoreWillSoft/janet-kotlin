package io.janet.kotlin

import io.janet.ActionHolder
import io.janet.JanetException

interface ActionServiceWrapperBody {

    fun onInterceptSend(evaluateBody: ActionServiceWrapperBody.(holder: ActionHolder<*>) -> Boolean)

    fun onInterceptCancel(evaluateBody: ActionServiceWrapperBody.(holder: ActionHolder<*>) -> Unit)

    fun onInterceptStart(evaluateBody: ActionServiceWrapperBody.(holder: ActionHolder<*>) -> Unit)

    fun onInterceptProgress(evaluateBody: ActionServiceWrapperBody.(holder: ActionHolder<*>, progress: Int) -> Unit)

    fun onInterceptSuccess(evaluateBody: ActionServiceWrapperBody.(holder: ActionHolder<*>) -> Unit)

    fun onInterceptFail(evaluateBody: ActionServiceWrapperBody.(holder: ActionHolder<*>, e: JanetException) -> Boolean)
}

