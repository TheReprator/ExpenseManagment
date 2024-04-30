package dev.reprator.accountbook.utility.httpPlugin

data class ResultDTOResponse<T>(val statusCode: Int, val data: T)
