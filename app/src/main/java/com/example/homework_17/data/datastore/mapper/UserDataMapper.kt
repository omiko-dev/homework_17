package com.example.homework_17.data.datastore.mapper

import com.example.application.UserData
import com.example.homework_17.domain.datastore_proto.DataStoreResponse

fun UserData.toDomain(): DataStoreResponse{
    return DataStoreResponse(
        inLogin = isLogIn,
        email = email
    )
}