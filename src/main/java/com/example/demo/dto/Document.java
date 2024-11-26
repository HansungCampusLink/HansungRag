package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
public class Document {

    @NotBlank
    private String content;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.S")
    private Timestamp date;

    @NotBlank
    private String title;

    @NotNull
    private String link;

    @NotBlank
    private String author;

    @NotBlank
    private String category;

    public Map<String, Object> toSpringMetadata() {
        return Map.of(
                "date", this.date.getTime(),
                "title", this.title,
                "link", this.link,
                "author", this.author,
                "category", this.category
        );
    }

    public org.springframework.ai.document.Document toSpringAiDocument() {
        return new org.springframework.ai.document.Document(this.content, this.toSpringMetadata());
    }
}
