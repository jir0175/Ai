
package com.aimentor.ai_mentor.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GeminiServiceTest {

    @Test
    void shouldGetResponseFromGemini() {
        GeminiService geminiService = new GeminiService();

        String response = geminiService.ask(
                "Explain what Java is in one short sentence."
        );

        assertNotNull(response);
        assertFalse(response.isBlank());

        System.out.println(response);
    }
}
