package com.android.projectakhirpam.dependenciesinjection

import com.android.projectakhirpam.repository.*
import com.android.projectakhirpam.service.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val produkRepository: ProdukRepository
    val kategoriRepository: KategoriRepository
    val pemasokRepository: PemasokRepository
    val merkRepository: MerkRepository
}

class ApplicationsContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val produkService: produkService by lazy {
        retrofit.create(produkService::class.java)
    }
    private val kategoriService: kategoriService by lazy {
        retrofit.create(kategoriService::class.java)
    }
    private val pemasokService: pemasokService by lazy {
        retrofit.create(pemasokService::class.java)
    }
    private val merkService: merkService by lazy {
        retrofit.create(merkService::class.java)
    }

    override val produkRepository: ProdukRepository by lazy {
        NetworkProdukRepository(produkService)
    }
    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }
    override val pemasokRepository: PemasokRepository by lazy {
        NetworkPemasokRepository(pemasokService)
    }
    override val merkRepository: MerkRepository by lazy {
        NetworkMerkRepository(merkService)
    }
}
