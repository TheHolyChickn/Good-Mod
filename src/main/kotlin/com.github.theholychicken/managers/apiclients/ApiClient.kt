package com.github.theholychicken.managers.apiclients

/**
 * Interface providing support for fetching data from an API source.
 */
interface ApiClient {
    /**
     * Pulls all auction data from the specific API source's implementation.
     */
    suspend fun fetchAllAuctions()
}