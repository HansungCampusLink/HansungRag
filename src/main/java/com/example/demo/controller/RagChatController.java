package com.example.demo.controller;

import com.example.demo.dto.ChatDataDto;

import com.example.demo.dto.MessageDto;
import com.example.demo.dto.RagChatDto;
import com.example.demo.service.ChatDataService;
import com.example.demo.service.NavigationService;
import com.example.demo.service.RagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class RagChatController {
    private final ChatDataService chatDataService;
    private final RagService ragService;
    private final NavigationService navigationService;

    @PostMapping
    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {

        List<MessageDto> messages = chatDataDto.getMessages();

        // 마지막 메시지의 content 가져오기
        MessageDto lastMessage = messages.get(messages.size() - 1);
        String query = lastMessage.getContent();


        String content = "";
        List<String> refLists = null;
        String destination = null;
        if (navigationService.isNavigationQuery(query)) {
            NavigationService.NavigationOutputDto navigate = navigationService.navigate(query);

            content = navigate.content();
            destination = navigate.destination();
            refLists = navigate.refLists();
        }
        else {
            RagService.RagOutputDto ragOutPut = ragService.getRagOutPut(chatDataDto);

            content = ragOutPut.content();
            refLists = ragOutPut.refLists();
        }


        MessageDto assistantMsg = new MessageDto("assistant", content, refLists, destination);

        Long chatID = chatDataDto.getChatId();
        if (chatDataDto.getChatId() == null) {
            chatID = chatDataService.saveChatData(chatDataDto, assistantMsg);
        }
        else {
            chatDataService.addMessage(chatID, lastMessage, assistantMsg);
        }

        RagChatDto ragChatDto = new RagChatDto(chatID, assistantMsg);


        return ResponseEntity.ok(ragChatDto);
    }

}