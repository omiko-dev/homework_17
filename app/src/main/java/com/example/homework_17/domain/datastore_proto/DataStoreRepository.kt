package com.example.homework_17.domain.datastore_proto

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun getData(): Flow<DataStoreResponse>
    suspend fun setData(dataStoreRequest: DataStoreRequest)
    suspend fun clearData()
}