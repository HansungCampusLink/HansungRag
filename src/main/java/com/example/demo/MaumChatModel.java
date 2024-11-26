package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class MaumChatModel implements ChatModel {

    private final String API_URL = "https://norchestra.maum.ai/harmonize/dosmart";
    private final String APP_ID;
    private final String MODEL_NAME = "hansung_70b_chat";
    private final String MODEL_ITEM = "maumgpt-maal2-70b-chat";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    public MaumChatModel(@Value("${maum.app_id}") String APP_ID) {
        this.APP_ID = APP_ID;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        List<Request.Message> messages = prompt.getInstructions().stream().map(instruction -> {
            MessageType messageType = instruction.getMessageType();
            String role = switch (messageType) {
                case SYSTEM -> Request.Message.ROLE_SYSTEM;
                case USER -> Request.Message.ROLE_USER;
                case ASSISTANT -> Request.Message.ROLE_ASSISTANT;
                default -> Request.Message.ROLE_USER;
            };
            String content = instruction.getContent();

            return new Request.Message(role, content);
        }).toList();


        Request request = new Request(APP_ID, MODEL_NAME, List.of(MODEL_ITEM), List.of(new Request.Param(messages, prompt.getOptions())));
        String requestBody;
        try {
             requestBody = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);


        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String content = (String) response.getBody().get("text");
            AssistantMessage assistantMessage = new AssistantMessage(content);
            return ChatResponse.builder()
                    .withGenerations(List.of(new Generation(assistantMessage)))
                    .build();
        } else {
            return ChatResponse.builder().build();
        }
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return new ChatOptions() {
            @Override
            public String getModel() {
                return MODEL_ITEM;
            }

            @Override
            public Double getFrequencyPenalty() {
                return 0.0;
            }

            @Override
            public Integer getMaxTokens() {
                return 1000;
            }

            @Override
            public Double getPresencePenalty() {
                return 0.0;
            }

            @Override
            public java.util.List<String> getStopSequences() {
                return null;
            }

            @Override
            public Double getTemperature() {
                return 0.9;
            }

            @Override
            public Integer getTopK() {
                return 1;
            }

            @Override
            public Double getTopP() {
                return 0.6;
            }

            @Override
            public ChatOptions copy() {
                return this;
            }
        };
    }


    @Getter
    public static class Request {
        private final String app_id;
        private final String name;
        private final List<String> item;
        private final List<Param> param;

        public Request(String app_id, String name, List<String> item, List<Param> param) {
            this.app_id = app_id;
            this.name = name;
            this.item = item;
            this.param = param;
        }

        @Getter
        public static class Param {
            private final List<Message> utterances;
            private final ChatOptions config;

            public Param(List<Message> utterances, ChatOptions config) {
                this.utterances = utterances;
                this.config = config;
            }
        }

        @Getter
        public static class Message {
            public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
            public static final String ROLE_USER = "ROLE_USER";
            public static final String ROLE_ASSISTANT = "ROLE_ASSISTANT";
            private final String role;
            private final String content;

            public Message(String role, String content) {
                this.role = role;
                this.content = content;
            }
        }
    }
}