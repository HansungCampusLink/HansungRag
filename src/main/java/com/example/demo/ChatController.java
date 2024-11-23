package com.example.demo;

import com.example.demo.service.NavigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient ragChatClient;

    private final NavigationService navigationService;

    private final OpenAiChatModel openAiChatModel;

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "농담해줘") String message) {
        String generation = this.ragChatClient
                .prompt()
                .user(message)
                .call()
                .content();
        return Map.of("generation", generation);
    }

    @GetMapping("/isNavigation")
    public boolean isNavigation(@RequestParam(value = "message", defaultValue = "상상관이 어디야?") String message) {
        return navigationService.isNavigationQuery(message);
    }

    @GetMapping("/test")
    public String test() {
        String jsonSchema = """
        {
            "type": "object",
            "properties": {
                "steps": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "explanation": { "type": "string" },
                            "output": { "type": "string" }
                        },
                        "required": ["explanation", "output"],
                        "additionalProperties": false
                    }
                },
                "final_answer": { "type": "string" }
            },
            "required": ["steps", "final_answer"],
            "additionalProperties": false
        }
        """;

        Prompt prompt = new Prompt("how can I solve 8x + 7 = -23",
                OpenAiChatOptions.builder()
                        .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI)
                        .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                        .build());

        ChatResponse response = this.openAiChatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }
}