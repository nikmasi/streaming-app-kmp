package com.streaming.spring_boot.playback.service

import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path

@Service
class PlaybackService(
) {
    fun convertToHls(input: Path, outputDir: Path) {
        Files.createDirectories(outputDir)

        val process = ProcessBuilder(
            "ffmpeg",
            "-i", input.toString(),
            "-codec:", "copy",
            "-start_number", "0",
            "-hls_time", "6",
            "-hls_list_size", "0",
            "-f", "hls",
            outputDir.resolve("master.m3u8").toString()
        )
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().use {
            it.lines().forEach(::println)
        }

        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw RuntimeException("FFmpeg failed with exit code $exitCode")
        }
    }
}