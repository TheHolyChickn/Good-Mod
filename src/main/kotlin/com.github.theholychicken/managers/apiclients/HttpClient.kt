package com.github.theholychicken.managers.apiclients

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.utils.modMessage
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * An Http client for managing API pulls
 */
object HttpClient {
    private val httpClient = OkHttpClient()
    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor { r ->
        Thread(r, "http-client-executor")
    }

    fun sendRequest(url: String): String {
        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                modMessage("Error sending request to $url")
                modMessage("API request was unsuccessful: ${response.code} ${response.message}")
                throw IOException("API request was unsuccessful: ${response.code} ${response.message}")
            }
            return response.body?.string() ?: throw IOException("Empty response")
        }
    }

    fun scheduleApiPulls() {
        executor.scheduleAtFixedRate({
            runBlocking {
                try {
                    GoodMod.logger.info("Beginning API Fetch")
                    when (GuiConfig.api) {
                        "HypixelApi" -> HypixelApiClient.fetchAllAuctions()
                        "CoflApi" -> CoflApiClient.fetchAllAuctions()
                        "TrickedApi" -> TrickedApiClient.fetchAllAuctions()
                    }
                } catch (e: Exception) {
                    GoodMod.logger.error("Error in API Fetch", e)
                }
            }
        }, 0, 30, TimeUnit.MINUTES)
    }
}