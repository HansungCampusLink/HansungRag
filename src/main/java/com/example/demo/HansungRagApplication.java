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


	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
		return new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults(), """

			Context information is below, surrounded by ---------------------

			---------------------
			{question_answer_context}
			---------------------

			Given the context and provided history information and not prior knowledge,
			reply to the user comment. If the answer is not in the context, inform
			the user that you can't answer the question. 너는 질문하는 사람이 어떤 사람인지, 전공이 무엇인지 안다면 고려해서 답변을 해줘야 해.
			""") ;
	}

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
