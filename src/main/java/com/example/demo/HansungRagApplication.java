package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HansungRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(HansungRagApplication.class, args);
	}
	@Bean
	ChatClient chatClient(ChatClient.Builder builder, QuestionAnswerAdvisor questionAnswerAdvisor) {
		return builder
				.defaultAdvisors(questionAnswerAdvisor)
				.build();
	}

	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
		return new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults());
	}

}
