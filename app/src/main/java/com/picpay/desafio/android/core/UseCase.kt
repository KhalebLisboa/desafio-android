package com.picpay.desafio.android.core

import kotlinx.coroutines.flow.Flow

abstract class UseCase<Source> {
    abstract suspend fun execute() : Flow<Source>
}