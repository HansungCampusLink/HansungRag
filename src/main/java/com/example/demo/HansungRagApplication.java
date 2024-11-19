package com.example.demo;

import com.example.demo.vector.PineconeVectorStore;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HansungRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(HansungRagApplication.class, args);
	}


	private static final String defaultSystem = "너는 한성대학교의 키오스크야. 사람들에게 한성대학교에 대한 질문을 받으면 친절하게 답변해줘";
	@Bean
	ChatClient chatClient(ChatClient.Builder builder, QuestionAnswerAdvisor questionAnswerAdvisor) {
		return builder
				.defaultAdvisors(questionAnswerAdvisor)
				.defaultSystem(defaultSystem)
				.build();
	}

	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
		return new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults(),
				"""

			Context information is below, surrounded by ---------------------

			---------------------
			{question_answer_context}
			---------------------

			Given the context and provided history information and not prior knowledge,
			reply to the user comment. If the answer is not in the context, inform
			the user that you can't answer the question. 그리고 질문하는사람의 신분과 전공을 출력해봐 
			""");
	}

//	@Bean
//	CustomQuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
//		return new CustomQuestionAnswerAdvisor(vectorStore, SearchRequest.defaults());
//	}

	@Bean
	VectorStore vectorStore(PineconeVectorStore.PineconeVectorStoreConfig pineconeVectorStoreConfig, EmbeddingModel embeddingModel) {
		return new PineconeVectorStore(pineconeVectorStoreConfig, embeddingModel);
	}

	@Bean
	PineconeVectorStore.PineconeVectorStoreConfig pineconeVectorStoreConfig(
			@Value("${spring.ai.vectorstore.pinecone.api-key}") String apiKey,
			@Value("${spring.ai.vectorstore.pinecone.index-name}") String indexName,
			@Value("${spring.ai.vectorstore.pinecone.namespace}") String namespace
	) {
		var builder = PineconeVectorStore.PineconeVectorStoreConfig.builder()
				.withApiKey(apiKey)
				.withIndexName(indexName)
				.withNamespace(namespace);

		return new PineconeVectorStore.PineconeVectorStoreConfig(builder);
	}

}