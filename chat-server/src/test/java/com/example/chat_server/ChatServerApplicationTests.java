package com.example.chat_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.chat_server.ChatServerApplication;

@SpringBootTest(classes = ChatServerApplication.class)  // Explicitly specify the main class
public class ChatServerApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }
}
