package com.example.vendox.citytrack.Data

import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Data.Repository.AuthRepositoryImpl


class RepositoryProvider {
    companion object {
        fun getAuthRepository(): AuthRepository {
            return AuthRepositoryImpl()
        }
    }
}