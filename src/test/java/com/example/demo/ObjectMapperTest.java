package com.example.demo;

import com.example.demo.chatClient.NavigationCheckDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ObjectMapperTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        // Given
        String json = """
        {
            "navigation": true
        }
        """;

        // When
        NavigationCheckDto navigationCheckDto = objectMapper.readValue(json, NavigationCheckDto.class);

        // Then
        assertThat(navigationCheckDto.isNavigation()).isTrue();
    }
}
