package com.example.demo;

import com.example.demo.vector.VectorUtils;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@SpringBootTest
public class SearchTest {
    @Autowired
    private VectorStore vectorStore;


    @Test
    public void searchTest() {
        // 검색 테스트
        var searchResult = vectorStore.similaritySearch("2024 공모전에 대해 알려줘");
        Document doc = searchResult.get(0);

        System.out.println("doc.getId() = " + doc.getId());
        Map<String, Object> metadata = doc.getMetadata();
        double date = (Double) metadata.get("date");

        System.out.println("====================================");
        System.out.println(VectorUtils.getLocalDateTimeFromDouble((Double) metadata.get("date")));
        System.out.println("====================================");

        System.out.println(date);
        long l = BigDecimal.valueOf(date).longValue();

        System.out.println(l);

        LocalDateTime dateTimeKST = LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.of("Asia/Seoul"));

        System.out.println(dateTimeKST);

//        System.out.println(date);

//        System.out.println(searchResult);
    }
}
