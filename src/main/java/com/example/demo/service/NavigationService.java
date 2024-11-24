package com.example.demo.service;

import com.example.demo.chatClient.NavigationCheckDto;
import com.example.demo.chatClient.NavigationDto;
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

import java.util.List;

import static com.example.demo.service.NavigationConst.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NavigationService {
    private final OpenAiChatModel openAiChatModel;
    private final ObjectMapper objectMapper;

    private final OpenAiChatOptions navCheckJsonOptions = OpenAiChatOptions.builder()
            .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI)
            .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, NAV_CHECK_SCHEMA))
            .build();

    private final OpenAiChatOptions navJsonOptions = OpenAiChatOptions.builder()
            .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI)
            .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, NAV_SCHEMA))
            .build();

    public boolean isNavigationQuery(String query) {
        Prompt prompt = new Prompt(CHECK_NAV_PRE_PROMPT + query + CHECK_NAV_AFTER_PROMPT, navCheckJsonOptions);

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

    public NavigationOutputDto navigate(String query) {
        Prompt prompt = new Prompt(NAV_PRE_PROMPT + query + NAV_AFTER_PROMPT, navJsonOptions);

        ChatResponse chatResponse = openAiChatModel.call(prompt);

        String content = chatResponse.getResult().getOutput().getContent();

        log.info("content: {}", content);

        NavigationDto navigationDto = null;
        try {
            navigationDto = objectMapper.readValue(content, NavigationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String destination = transformDestination(navigationDto.getDestination());

        if (destination == null) {
            return new NavigationOutputDto(navigationDto.getContent(), null, List.of());
        }
        return new NavigationOutputDto(navigationDto.getContent(), destination, List.of(getDestinationMapLink(destination)));
    }

    String transformDestination(String destination) {
        return switch (destination) {
            case "공학관 A동" -> "공학관 에이동";
            case "공학관 B동" -> "공학관 비동";
            case "none" -> null;
            default -> destination;
        };
    }


    public static record NavigationOutputDto(String content, String destination, List<String> refLists) {}

}
