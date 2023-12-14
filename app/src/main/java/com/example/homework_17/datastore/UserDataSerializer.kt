package com.example.homework_17.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.application.UserData
import com.example.homework_17.App
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

val Context.dataStore: DataStore<UserData> by dataStore(
    fileName = "user_data.proto",
    serializer = UserDataSerializer
)

object UserDataSerializer : Serializer<UserData> {
    override val defaultValue: UserData = UserData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserData {
        try {
            return UserData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UserData,
        output: OutputStream
    ) = t.writeTo(output)

    val userDataFlow: Flow<UserData> = App.application.dataStore.data
        .map { userData ->
            userData
        }


   suspend fun setUserData(newUserData: SessionUserData) {
           App.application.applicationContext.dataStore.updateData {
               it.toBuilder()
                   .setEmail(newUserData.email)
                   .setIsLogIn(newUserData.isLoggedIn)
                   .build()
           }
   }

    suspend fun clearUserData(){
        App.application.applicationContext.dataStore.updateData {
            it.toBuilder().clear().build()
        }
    }

}
