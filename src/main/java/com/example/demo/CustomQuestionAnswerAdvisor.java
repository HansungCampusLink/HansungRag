package com.example.demo;

import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

public class CustomQuestionAnswerAdvisor extends QuestionAnswerAdvisor {
    private static final String CUSTOM_USER_TEXT_ADVISE = """

        Context information is below, surrounded by ---------------------

        ---------------------
        {question_answer_context}
        ---------------------

        Given the context and provided history information and not prior knowledge,
        reply to the user comment. If the answer is not in the context, inform
        the user that you can't answer the question.
        """;


    public CustomQuestionAnswerAdvisor(VectorStore vectorStore) {
        super(vectorStore, SearchRequest.defaults(), CUSTOM_USER_TEXT_ADVISE);
    }

    public CustomQuestionAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest) {
        super(vectorStore, searchRequest, CUSTOM_USER_TEXT_ADVISE);
    }

    public CustomQuestionAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest, String userTextAdvise) {
        super(vectorStore, searchRequest, CUSTOM_USER_TEXT_ADVISE);
    }

    public CustomQuestionAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest, String userTextAdvise, boolean protectFromBlocking) {
        super(vectorStore, searchRequest, CUSTOM_USER_TEXT_ADVISE, protectFromBlocking);
    }

    public CustomQuestionAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest, String userTextAdvise, boolean protectFromBlocking, int order) {
        super(vectorStore, searchRequest, CUSTOM_USER_TEXT_ADVISE, protectFromBlocking, order);
    }
}
