package com.example.demo.csv;

import com.example.demo.dto.Document;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvMessageConverter implements HttpMessageConverter<List<Document>> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return List.class.isAssignableFrom(clazz) && mediaType.equalsTypeAndSubtype(MediaType.valueOf("text/csv"));
    }

    @Override
    public List<Document> read(Class<? extends List<Document>> clazz, HttpInputMessage inputMessage) throws IOException {
        return new CsvToBeanBuilder<Document>(new InputStreamReader(inputMessage.getBody()))
                .withType(Document.class)
                .build()
                .parse();
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.valueOf("text/csv"));
    }

    @Override
    public void write(List<Document> documents, MediaType contentType, HttpOutputMessage outputMessage) throws IOException {
        throw new UnsupportedOperationException("쓰기 기능은 지원하지 않습니다.");
    }
}