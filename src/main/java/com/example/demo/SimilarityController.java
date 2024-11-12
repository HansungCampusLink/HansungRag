package com.example.demo;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SimilarityController {

    private final VectorStore vectorStore;

    @Autowired
    public SimilarityController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/ai/similarity")
    public ResponseEntity<List<String>> findSimilar(@RequestParam(defaultValue = "spring") String query) {
        // 하드코딩된 문서에서 유사한 문서 검색
        List<Document> similarDocuments = vectorStore.similaritySearch(query);

        // 결과에서 메시지 리스트 추출
        List<String> resultMessages = similarDocuments.stream()
                .map(Document::getContent) // Document에서 메시지 추출
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultMessages);
    }
}



