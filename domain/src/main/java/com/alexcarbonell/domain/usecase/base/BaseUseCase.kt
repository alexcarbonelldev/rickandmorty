package com.alexcarbonell.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseUseCase<P, R> {

    protected abstract suspend fun func(params: P): R

    fun execute(
        scope: CoroutineScope,
        params: P,
        onSuccess: (R) -> Unit,
        onError: (Exception) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    func(params)
                }
                onSuccess(result)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
