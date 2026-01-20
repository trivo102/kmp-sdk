package org.example.export

/**
 * Main SDK class - Entry point for third-party developers
 */
object ExportSDK {
    private var isInitialized = false
    private var config: SDKConfig? = null

    /**
     * Initialize the SDK with configuration
     */
    fun initialize(config: SDKConfig) {
        this.config = config
        isInitialized = true
    }

    /**
     * Check if SDK is initialized
     */
    fun isInitialized(): Boolean = isInitialized

    /**
     * Get current configuration
     */
    fun getConfig(): SDKConfig? = config

    /**
     * Get SDK version
     */
    fun getVersion(): String = "1.0.0"
}

/**
 * SDK Configuration class
 */
data class SDKConfig(
    val apiKey: String,
    val environment: Environment = Environment.PRODUCTION,
    val enableLogging: Boolean = false
)

enum class Environment {
    DEVELOPMENT,
    STAGING,
    PRODUCTION
}
