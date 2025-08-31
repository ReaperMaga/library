package com.github.reapermaga.library.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DownloadTest {
    @Test
    fun `Download google html`() {
        val stream =
            download("https://google.com") {
                useBrowserAgent()
            }
        Assertions.assertTrue(stream.readAllBytes().isNotEmpty())
    }
}
