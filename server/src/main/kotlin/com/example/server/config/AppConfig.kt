package com.example.server.config

import java.io.File

data class AppConfig(
    val port: Int,
    val publicBaseUrl: String,
    val corsAllowedOrigins: List<String>,
    val telegramBotToken: String,
    val telegramWebhookUrl: String,
    val telegramAdminIds: Set<Long>,
    val openAiApiKey: String,
    val transcriptionModel: String,
    val geminiApiKey: String,
    val geminiModel: String,
    val ffmpegBin: String,
    val workDir: File,
    val telegramInitDataMaxAgeSeconds: Long,
    val telegramRegisterWebhook: Boolean,
    val pipelineProjectDir: File,
    val pipelineDryRunCommand: String,
    val edgeSharedSecret: String,
) {
    companion object {
        fun fromEnv() = AppConfig(
            port = env("PORT", "8080").toInt(),
            publicBaseUrl = env("PUBLIC_BASE_URL", "http://localhost:8080"),
            corsAllowedOrigins = env("CORS_ALLOWED_ORIGINS", "http://localhost:3000").split(',').map { it.trim() }.filter { it.isNotEmpty() },
            telegramBotToken = env("TELEGRAM_BOT_TOKEN", ""),
            telegramWebhookUrl = env("TELEGRAM_WEBHOOK_URL", ""),
            telegramAdminIds = env("TELEGRAM_ADMIN_IDS", "").split(',').mapNotNull { it.trim().toLongOrNull() }.toSet(),
            openAiApiKey = env("OPENAI_API_KEY", ""),
            transcriptionModel = env("OPENAI_TRANSCRIPTION_MODEL", "whisper-1"),
            geminiApiKey = env("GEMINI_API_KEY", ""),
            geminiModel = env("GEMINI_MODEL", "gemini-3.6-flash"),
            ffmpegBin = env("FFMPEG_BIN", "ffmpeg"),
            workDir = File(env("WORK_DIR", "./server/data")).apply { mkdirs() },
            telegramInitDataMaxAgeSeconds = env("TELEGRAM_INITDATA_MAX_AGE_SECONDS", "86400").toLong(),
            telegramRegisterWebhook = env("TELEGRAM_REGISTER_WEBHOOK", "false").toBoolean(),
            pipelineProjectDir = File(env("PIPELINE_PROJECT_DIR", ".")),
            pipelineDryRunCommand = env("PIPELINE_DRY_RUN_COMMAND", "npm run dry-run --prefix pipeline-ui"),
            edgeSharedSecret = env("EDGE_SHARED_SECRET", ""),
        )

        private fun env(name: String, default: String) = System.getenv(name)?.takeIf { it.isNotBlank() } ?: default
    }
}
