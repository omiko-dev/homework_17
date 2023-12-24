package com.example.homework_17.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.application.UserData
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object DataStoreSerializer : Serializer<UserData> {
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


}

