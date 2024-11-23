package com.example.demo.chatClient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {


    private static final String DEFAULT_SYSTEM_MESSAGE = """
			너는 한성대학교 키오스크의 안내원 "탐정부기" 야.
			사람들에게 질문을 받으면, 친절하게 답변을 해줘.
			""";

    @Bean
    ChatClient ragChatClient(ChatClient.Builder builder, QuestionAnswerAdvisor questionAnswerAdvisor) {
        return builder
                .clone()
                .defaultSystem(DEFAULT_SYSTEM_MESSAGE)
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }

    @Bean
    ChatClient navigationChatClient(ChatClient.Builder builder) {
        return builder
                .clone()
                .defaultSystem("""
                너는 친절한 한성대학교의 navigation assistant야.
                너는 길찾기에 대한 질문을 받게될거야.
                너는 목적지가 어디인지 파악해서 가는 방법을 설명해줘.
                """)
                .build();
    }

    public static final String prompt = """
    Please respond in JSON format. The JSON should represent whether the given input is a navigation-related query.
    Input: {input}
    The response should strictly follow this structure:
    {
        "isNavigation": <boolean>
    }
    For example:
    - If the input is "How do I get to the nearest park?", respond with {"isNavigation": true}.
    - If the input is "What is the weather today?", respond with {"isNavigation": false}.
    Do not include any extra text, explanations, or formatting outside of the JSON structure.
""";

    private static final String navigationSchema = """
    {
        "type": "object",
        "properties": {
            "isNavigation": {
                "type": "boolean",
                "description": "Indicates whether the input is a navigation-related query"
            }
        },
        "required": ["isNavigation"]
    }
    """;

    @Bean
    ChatClient navigationCheckChatClient(ChatClient.Builder builder, OpenAiChatModel openAiChatModel) {
        return builder
                .clone()
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_OBJECT, navigationSchema))
                        .build())
                .build();
    }
}