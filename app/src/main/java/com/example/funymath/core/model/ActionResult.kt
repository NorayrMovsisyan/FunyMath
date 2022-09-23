package droid.telemed.mts.ru.telemed.core

sealed class ActionResult<out S> {
    data class Success<S>(val data: S) : ActionResult<S>()
    data class Error(val errors: CallException) : ActionResult<Nothing>()
}