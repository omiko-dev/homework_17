package com.example.homework_17.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.application.UserData
import com.example.homework_17.data.datastore.mapper.toDomain
import com.example.homework_17.domain.datastore_proto.DataStoreRepository
import com.example.homework_17.domain.datastore_proto.DataStoreRequest
import com.example.homework_17.domain.datastore_proto.DataStoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<UserData> by dataStore(
    fileName = "user_data.pb", serializer = DataStoreSerializer
)

class DataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {
    override suspend fun getData(): Flow<DataStoreResponse> =
        context.dataStore.data
            .map {
                it.toDomain()
            }

    override suspend fun setData(dataStoreRequest: DataStoreRequest) {
        context.dataStore.updateData {
            it.toBuilder()
                .setEmail(dataStoreRequest.email)
                .setIsLogIn(dataStoreRequest.inLogin)
                .build()
        }
    }

    override suspend fun clearData() {
        context.dataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}