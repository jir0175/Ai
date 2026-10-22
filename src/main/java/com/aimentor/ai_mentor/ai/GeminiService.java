
package com.aimentor.ai_mentor.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class GeminiService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    public GeminiService() {
        this.apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Environment variable GEMINI_API_KEY is not set"
            );
        }

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.objectMapper = new ObjectMapper();
    }

    public String ask(String prompt) {
        try {
            ObjectNode body = objectMapper.createObjectNode();

            var contents = body.putArray("contents");
            var content = contents.addObject();
            content.put("role", "user");

            var parts = content.putArray("parts");
            parts.addObject().put("text", prompt);

            ObjectNode generationConfig =
                    body.putObject("generationConfig");
            generationConfig.put("maxOutputTokens", 1500);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://generativelanguage.googleapis.com/"
                            + "v1beta/models/gemini-2.5-flash:generateContent"
                    ))
                    .timeout(Duration.ofSeconds(90))
                    .header("x-goog-api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(body)
                    ))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200
                    || response.statusCode() >= 300) {
                throw new IllegalStateException(
                        "Gemini returned HTTP "
                                + response.statusCode()
                                + ": " + response.body()
                );
            }

            JsonNode partsNode = objectMapper.readTree(response.body())
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts");

            StringBuilder answer = new StringBuilder();

            for (JsonNode part : partsNode) {
                JsonNode textNode = part.path("text");

                if (textNode.isTextual()) {
                    answer.append(textNode.asText());
                }
            }

            if (answer.isEmpty() || answer.toString().isBlank()) {
                throw new IllegalStateException(
                        "Gemini returned an empty response"
                );
            }

            return answer.toString();

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to communicate with Gemini", e
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Gemini request was interrupted", e
            );
        }
    }
}
