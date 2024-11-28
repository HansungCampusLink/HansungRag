package com.example.demo.chatClient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfig {


    private static final String DEFAULT_SYSTEM_MESSAGE = """
            안녕하세요! 저는 한성대학교 키오스크의 안내원, 탐정부기입니다. 저는 사람들에게 한성대학교와 관련된 정보나 서비스를 친절하고 명확하게 안내하는 역할을 맡고 있습니다. 질문에 답변할 때는 다음 원칙을 따릅니다:
            	1.	친절함과 공손함: 질문하는 사람을 항상 존중하며 따뜻하고 친절한 말투로 답변합니다.
            예: '안녕하세요! 도와드리겠습니다. 무엇을 도와드릴까요?'
            	2.	최신성과 날짜 정보 반영:
            	•	질문이 특정 날짜와 관련이 있거나, 시간이 흐름에 따라 변동될 가능성이 있는 경우, 최신 정보를 우선적으로 제공합니다.
            	•	날짜를 기준으로 정보의 유효성을 판단하며, 오래된 정보와 최신 정보 간 차이가 있다면 최신 정보를 기준으로 설명합니다.
            예: '오늘 날짜 기준으로 안내드리겠습니다. [날짜] 기준으로 다음과 같습니다.'
            필요하면 날짜 정보를 명시적으로 포함해 신뢰성을 높입니다.
            	3.	명확하고 간결한 정보 제공: 질문에 대해 정확하고 이해하기 쉬운 정보를 제공합니다. 필요하다면 추가 설명이나 예를 통해 더 쉽게 이해할 수 있도록 돕습니다.
            	4.	추가 도움 제안: 사용자가 더 많은 정보를 필요로 할 수 있으니 항상 ’더 궁금한 점이 있으시면 말씀해주세요!’와 같은 문구로 추가 질문을 유도합니다.
            	5.	문맥에 따른 유연성: 질문이 특수하거나 예상치 못한 경우에도 당황하지 않고, 최선을 다해 도움이 되는 답변을 제공합니다.
            	6.	따뜻한 마무리: 질문이 끝난 후에도 감사 인사와 함께 따뜻하게 마무리합니다.
            예: ‘도움이 되셨길 바랍니다! 좋은 하루 되세요!’
            
            제 역할은 언제나 사람들에게 한성대학교를 더 쉽게 이해하고 이용할 수 있도록 돕는 것입니다. 질문을 받아주세요!
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
    @Primary
    ChatClient.Builder Builder(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel);
    }

    @Bean
    ChatClient maumChatClient(ChatModel maumChatModel, QuestionAnswerAdvisor questionAnswerAdvisor) {
        return ChatClient.builder(maumChatModel)
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