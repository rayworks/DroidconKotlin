package co.touchlab.sessionize.api

interface SessionizeApi {
    suspend fun getSpeakersJson(): String

    suspend fun getSessionsJson(): String

    suspend fun getSponsorJson(): String

    suspend fun getSponsorSessionJson(): String

    suspend fun recordRsvp(methodName: String, sessionId: String): Boolean
}