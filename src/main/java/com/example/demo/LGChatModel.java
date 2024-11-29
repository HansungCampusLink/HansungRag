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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
//public class LGChatModel implements ChatModel {
//
//    private final String API_URL = "http://localhost:8000";
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//
//    @Override
//    public ChatResponse call(Prompt prompt) {
//        List<LGChatModel.Request.Message> messages = prompt.getInstructions().stream().map(instruction -> {
//            MessageType messageType = instruction.getMessageType();
//            String role = switch (messageType) {
//                case SYSTEM -> Request.Message.ROLE_SYSTEM;
//                case USER -> Request.Message.ROLE_USER;
//                case ASSISTANT -> Request.Message.ROLE_ASSISTANT;
//                default -> Request.Message.ROLE_USER;
//            };
//            String content = instruction.getContent();
//
//            return new Request.Message(role, content);
//        }).toList();
//
//
//        Map<String, Object> request = Map.of(
//                "inputs", Map.of(
//                        "messages", messages != null ? messages : List.of(),
//                        "config", prompt.getOptions()
//                )
//        );
//
//        String requestBody;
//        try {
//            requestBody = objectMapper.writeValueAsString(request);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
//
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            String content = (String) response.getBody().get("text");
//            AssistantMessage assistantMessage = new AssistantMessage(content);
//            return ChatResponse.builder()
//                    .withGenerations(List.of(new Generation(assistantMessage)))
//                    .build();
//        } else {
//            return ChatResponse.builder().build();
//        }
//    }
//
//    @Override
//    public ChatOptions getDefaultOptions() {
//        return new ChatOptions() {
//
//
//            @Override
//            public String getModel() {
//                return null;
//            }
//
//            @Override
//            public Double getFrequencyPenalty() {
//                return 0.0;
//            }
//
//            @Override
//            public Integer getMaxTokens() {
//                return 100;
//            }
//
//            @Override
//            public Double getPresencePenalty() {
//                return 0.0;
//            }
//
//            @Override
//            public java.util.List<String> getStopSequences() {
//                return null;
//            }
//
//            @Override
//            public Double getTemperature() {
//                return 0.9;
//            }
//
//            @Override
//            public Integer getTopK() {
//                return 1;
//            }
//
//            @Override
//            public Double getTopP() {
//                return 0.6;
//            }
//
//            @Override
//            public ChatOptions copy() {
//                return this;
//            }
//        };
//    }
//
//
//    @Getter
//    public static class Request {
//        private final List<Param> param;
//
//        public Request(List<LGChatModel.Request.Param> param) {
//            this.param = param;
//        }
//
//        @Getter
//        public static class Param {
//            private final List<Request.Message> utterances;
//            private final ChatOptions config;
//
//            public Param(List<Message> utterances, ChatOptions config) {
//                this.utterances = utterances;
//                this.config = config;
//            }
//        }
//
//        @Getter
//        public static class Message {
//            public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
//            public static final String ROLE_USER = "ROLE_USER";
//            public static final String ROLE_ASSISTANT = "ROLE_ASSISTANT";
//            private final String role;
//            private final String content;
//
//            public Message(String role, String content) {
//                this.role = role;
//                this.content = content;
//            }
//        }
//    }
//}


//여기부터
//@Component
//public class LGChatModel implements ChatModel {
//
//    private final String API_URL = "http://localhost:8000";
//    private final String MODEL_NAME = "LGChat";
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final RestTemplate restTemplate = new RestTemplate();
//
//
//    @Override
//    public ChatResponse call(Prompt prompt) {
//        // 메시지 내용을 하나의 문자열로 결합
//        StringBuilder inputsBuilder = new StringBuilder();
//        for (var instruction : prompt.getInstructions()) {
//            inputsBuilder.append(instruction.getContent()).append(" ");
//        }
//        String inputs = inputsBuilder.toString().trim();
//
//        // 기본 옵션 가져오기
//        ChatOptions options = getDefaultOptions();
//
//        // 요청 생성
//        Map<String, Object> request = Map.of(
//                "inputs", inputs,
//                "parameters", Map.of(
//                        "max_new_tokens", options.getMaxTokens(),
//                        "do_sample", options.getTemperature() > 0,
//                        "temperature", options.getTemperature(),
//                        "top_p", options.getTopP(),
//                        "top_k", options.getTopK(),
//                        "stop", options.getStopSequences(),
//                        "prompt", "You are EXAONE model from LG AI Research, a helpful assistant"
//                )
//        );
//
//        String requestBody;
//        try {
//            requestBody = objectMapper.writeValueAsString(request);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            String content = (String) response.getBody().get("text");
//            AssistantMessage assistantMessage = new AssistantMessage(content);
//            return ChatResponse.builder()
//                    .withGenerations(List.of(new Generation(assistantMessage)))
//                    .build();
//        } else {
//            return ChatResponse.builder().build();
//        }
//    }
//
//    @Override
//    public ChatOptions getDefaultOptions() {
//        return new ChatOptions() {
//            @Override
//            public String getModel() {
//                return MODEL_NAME;
//            }
//
//            @Override
//            public Double getFrequencyPenalty() {
//                return 0.0;
//            }
//
//            @Override
//            public Integer getMaxTokens() {
//                return 1000;
//            }
//
//            @Override
//            public Double getPresencePenalty() {
//                return 0.0;
//            }
//
//            @Override
//            public List<String> getStopSequences() {
//                return null;
//            }
//
//            @Override
//            public Double getTemperature() {
//                return 0.9;
//            }
//
//            @Override
//            public Integer getTopK() {
//                return 1;
//            }
//
//            @Override
//            public Double getTopP() {
//                return 0.6;
//            }
//
//            @Override
//            public ChatOptions copy() {
//                return this;
//            }
//        };
//    }
//
//    @Getter
//    public static class Request {
//        private final String model;
//        private final List<Message> messages;
//        private final ChatOptions options;
//
//        public Request(String model, List<Message> messages, ChatOptions options) {
//            this.model = model;
//            this.messages = messages;
//            this.options = options;
//        }
//
//        @Getter
//        public static class Message {
//            public static final String ROLE_SYSTEM = "system";
//            public static final String ROLE_USER = "user";
//            public static final String ROLE_ASSISTANT = "assistant";
//            private final String role;
//            private final String content;
//
//            public Message(String role, String content) {
//                this.role = role;
//                this.content = content;
//            }
//        }
//    }
//}

// 여기서부터 또
//@Component
//public class LGChatModel implements ChatModel {
//
//    private final String API_URL = "http://localhost:8000";
//    private final String MODEL_NAME = "LGChat";
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Override
//    public ChatResponse call(Prompt prompt) {
//        // 메시지 내용을 하나의 문자열로 결합
//        StringBuilder inputsBuilder = new StringBuilder();
//        for (var instruction : prompt.getInstructions()) {
//            inputsBuilder.append(instruction.getContent()).append(" ");
//        }
//        String inputs = inputsBuilder.toString().trim();
//
//        // 기본 옵션 가져오기
//        ChatOptions options = getDefaultOptions();
//
//        // 요청 생성
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("max_new_tokens", 124);
//        parameters.put("temperature", options.getTemperature());
//        parameters.put("stream", false);
//        parameters.put("do_sample", true);
//        parameters.put("top_p", options.getTopP());
//        parameters.put("top_k", 50);
//        parameters.put("repetition_penalty", 1.2);
//        parameters.put("stop", Arrays.asList("\n", "."));
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("inputs", inputs);
//        request.put("parameters", parameters);
//
//        String requestBody;
//        try {
//            requestBody = objectMapper.writeValueAsString(request);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            String content = (String) response.getBody().get("text");
//            AssistantMessage assistantMessage = new AssistantMessage(content);
//            return ChatResponse.builder()
//                    .withGenerations(List.of(new Generation(assistantMessage)))
//                    .build();
//        } else {
//            throw new RuntimeException("Failed to get response from LG Chat model");
//        }
//    }
//
//    @Override
//    public ChatOptions getDefaultOptions() {
//        return new ChatOptions() {
//            @Override
//            public String getModel() {
//                return MODEL_NAME;
//            }
//
//            @Override
//            public Double getFrequencyPenalty() {
//                return 0.0;
//            }
//
//            @Override
//            public Integer getMaxTokens() {
//                return 124;
//            }
//
//            @Override
//            public Double getPresencePenalty() {
//                return 0.0;
//            }
//
//            @Override
//            public List<String> getStopSequences() {
//                return Arrays.asList("\n", ".");
//            }
//
//            @Override
//            public Double getTemperature() {
//                return 0.7;
//            }
//
//            @Override
//            public Integer getTopK() {
//                return 50;
//            }
//
//            @Override
//            public Double getTopP() {
//                return 0.9;
//            }
//
//            @Override
//            public ChatOptions copy() {
//                return this;
//            }
//        };
//    }
//}

@Component
public class LGChatModel implements ChatModel {

    private final String API_URL = "http://localhost:8000";
    private final String MODEL_NAME = "LGChat";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ChatResponse call(Prompt prompt) {
        // 메시지 내용을 하나의 문자열로 결합
        StringBuilder inputsBuilder = new StringBuilder();
        for (var instruction : prompt.getInstructions()) {
            inputsBuilder.append(instruction.getContent()).append(" ");
        }
        String inputs = inputsBuilder.toString().trim();

        // 요청 생성
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_new_tokens", 124);
        parameters.put("temperature", 0.7);
        parameters.put("stream", false);
        parameters.put("do_sample", true);
        parameters.put("top_p", 0.9);
        parameters.put("top_k", 50);
        parameters.put("repetition_penalty", 1.2);
        parameters.put("stop", Arrays.asList("\n", "."));

        Map<String, Object> request = new HashMap<>();
        request.put("inputs", inputs);
        request.put("parameters", parameters);

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // 응답 타입을 List.class로 변경
        ResponseEntity<List> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                entity,
                List.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // 첫 번째 응답 요소를 가져옴
            List<?> responseBody = response.getBody();
            if (!responseBody.isEmpty()) {
                Object firstResponse = responseBody.get(0);

                // Map으로 캐스팅하여 처리
                if (firstResponse instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> responseMap = (Map<String, Object>) firstResponse;
                    String content = (String) responseMap.get("generated_text");
                    if (content == null) {
                        content = "No response generated";
                    }
                    AssistantMessage assistantMessage = new AssistantMessage(content);
                    return ChatResponse.builder()
                            .withGenerations(List.of(new Generation(assistantMessage)))
                            .build();
                }
            }
        }

        throw new RuntimeException("Failed to get valid response from LG Chat model");
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return new ChatOptions() {
            @Override
            public String getModel() {
                return MODEL_NAME;
            }

            @Override
            public Double getFrequencyPenalty() {
                return 0.0;
            }

            @Override
            public Integer getMaxTokens() {
                return 124;
            }

            @Override
            public Double getPresencePenalty() {
                return 0.0;
            }

            @Override
            public List<String> getStopSequences() {
                return Arrays.asList("\n", ".");
            }

            @Override
            public Double getTemperature() {
                return 0.7;
            }

            @Override
            public Integer getTopK() {
                return 50;
            }

            @Override
            public Double getTopP() {
                return 0.9;
            }

            @Override
            public ChatOptions copy() {
                return this;
            }
        };
    }
}