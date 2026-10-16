
package com.aimentor.ai_mentor.ai;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService() {
        String apiKey = System.getenv("GOOGLE_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Environment variable GOOGLE_API_KEY is not set"
            );
        }

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String ask(String prompt) {
        GenerateContentResponse response = client.models.generateContent(
                "gemini-flash-latest",
                prompt,
                null
        );

        return response.text();
    }
}
