package com.devbea.lotuskmm.domain.util

import com.devbea.lotuskmm.domain.model.GenericMessageInfo

data class DataState<T>(
    val message: GenericMessageInfo.Builder? = null,
    val data: T? = null,
    val isLoading: Boolean = false
) {
    companion object {
        fun <T> error(message: GenericMessageInfo.Builder?): DataState<T> = DataState(message = message)

        fun <T> data(message: GenericMessageInfo.Builder? = null, data: T? = null): DataState<T> =
            DataState(message = message, data = data)

        fun <T> loading(): DataState<T> = DataState(isLoading = true)
    }
}