package com.example.demo.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final VectorStore vectorStore;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore; // 생성자 주입
    }

        public List<String> findSimilarDocuments(String userContent) {
        // 유사도 검색 요청 생성
        SearchRequest request = SearchRequest
                .query(userContent);

        // 유사한 문서 검색
        List<Document> similarDocuments = vectorStore.similaritySearch(request);

        // 문서에서 링크 추출
        return similarDocuments.stream()
                .map(doc -> (String) doc.getMetadata().get("link"))
                .collect(Collectors.toList());
    }
}
