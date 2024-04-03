package com.ayou.ai;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("ai")
public class ChatAPI {
    private final OllamaChatClient chatClient;

    @Autowired
    public ChatAPI(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("generate")
    public Map generate(@RequestParam(value = "msg", defaultValue = "Tell me a joke") String message) {
        String call = chatClient.call(message);
        log.info(call);
        return Map.of("generation", call);
    }

    @GetMapping("generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }
}
