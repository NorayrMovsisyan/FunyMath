package droid.telemed.mts.ru.telemed.core

data class CallException(
    val errorCode: Int,
    val errorMessage: String? = null,
    val errorBody: RequestError? = null
): Exception(errorMessage)