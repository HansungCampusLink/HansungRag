package com.example.demo;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaumChatModel implements ChatModel {

    private static final String API_URL = "https://norchestra.maum.ai/harmonize/dosmart";
    private static final String APP_ID = "APP_ID";
    private static final String MODEL_NAME = "MODEL_NAME";
    private static final String MODEL_ITEM = "MODEL_ITEM";

    private final RestTemplate restTemplate;

    public MaumChatModel() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ChatResponse call(Prompt prompt) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("app_id", APP_ID);
        requestBody.put("name", MODEL_NAME);
        requestBody.put("item", new String[]{MODEL_ITEM});

        Map<String, Object> userUtterance = new HashMap<>();
        userUtterance.put("role", "ROLE_USER");
        userUtterance.put("content", prompt.getContents());

        Map<String, Object> systemUtterance = new HashMap<>();
        systemUtterance.put("role", "ROLE_SYSTEM");
        systemUtterance.put("content", "시스템 메시지");

        ChatOptions options = prompt.getOptions() != null ? prompt.getOptions() : getDefaultOptions();
        Map<String, Object> config = new HashMap<>();
        config.put("top_p", options.getTopP());
        config.put("top_k", options.getTopK());
        config.put("temperature", options.getTemperature());
        config.put("presence_penalty", options.getPresencePenalty());
        config.put("frequency_penalty", options.getFrequencyPenalty());
        config.put("repetition_penalty", 1.0);

        // param 배열 구성
        Map<String, Object> param = new HashMap<>();
        param.put("utterances", new Map[]{systemUtterance, userUtterance});
        param.put("config", config);

        requestBody.put("param", new Map[]{param});

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // API 호출
            ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                String content = (String) responseBody.get("content");
                return new ChatResponse(content);
            } else {
                // 오류 처리
                return new ChatResponse("오류가 발생했습니다.");
            }
        } catch (Exception e) {
            // 예외 처리
            return new ChatResponse("예외가 발생했습니다: " + e.getMessage());
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
}