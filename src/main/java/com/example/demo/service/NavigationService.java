package com.example.demo.service;

import com.example.demo.chatClient.NavigationCheckDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NavigationService {

    private final ChatClient navigationCheckChatClient;
    private final ChatClient navigationChatClient;
    private final OpenAiChatModel openAiChatModel;
    private final ObjectMapper objectMapper;

    private final String navigationSchema = """
    {
        "type": "object",
        "properties": {
            "navigation": {
                "type": "boolean"
            }
        },
        "required": ["navigation"],
        "additionalProperties": false
    }
    """;

    private final OpenAiChatOptions jsonOptions = OpenAiChatOptions.builder()
            .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI)
            .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, navigationSchema))
            .build();

    private static final String promptPre = """
    Please respond in JSON format. The json should represent whether the given input is a navigation-related query.
    Input: 
""";
    private static final String promptAfter = """
        
        The response should strictly follow this structure:
        {
            "navigation": boolean
        }
        For example:
        - If the input is "공학관이 어디야?", respond with {"navigation": true}.
        - If the input is "체육관까지 어떻게 가?", respond with {"navigation": true}.
        - If the input is "오늘 날씨는 어때?", respond with {"navigation": false}.
        Do not include any extra text, explanations, or formatting outside of the JSON structure.
    """;

    public boolean isNavigationQuery(String query) {
        Prompt prompt = new Prompt(promptPre + query + promptAfter, jsonOptions);

        ChatResponse chatResponse = openAiChatModel.call(prompt);

        String content = chatResponse.getResult().getOutput().getContent();

        log.info("content: {}", content);

        NavigationCheckDto navigationCheckDto = null;
        try {
            navigationCheckDto = objectMapper.readValue(content, NavigationCheckDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return navigationCheckDto.isNavigation();
    }

}
