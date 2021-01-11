package org.gamsiz.chatapp.controller;

import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.service.ConversationService;
import org.gamsiz.chatapp.util.ConversationTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConversationControllerWebClientIT {
    
    private static final String BASE_CONVERSATION_URL_TEMPLATE = "http://localhost:{0}/api/chat/v1/{1}/conversation";

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ConversationService conversationService;
    
    
    @Test
    public void testListConversationsForInitialSetup() {
        int size = readConversationListSize();
        assertEquals(0, size);
    }
    
    @Test
    public void testListConversations() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        conversationService.createConversation(conversationDTO);
        
        int size = readConversationListSize();
        assertEquals(1, size);
    }
    
    private int readConversationListSize() {
        return restTemplate.getForObject(
                MessageFormat.format(BASE_CONVERSATION_URL_TEMPLATE,
                        Integer.toString(port),
                        ConversationTestUtil.SAMPLE_USER_1),
                List.class).size();
    }

    @Test
    public void testCreateConversation() {
        int beforeSize = readConversationListSize();
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        
        ResponseEntity<Void> responseEntity =
                restTemplate.postForEntity(
                        MessageFormat.format(BASE_CONVERSATION_URL_TEMPLATE,
                                Integer.toString(port),
                                ConversationTestUtil.SAMPLE_USER_1),
                        conversationDTO, Void.class);
        
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
        
        int sizeAfterAddition = readConversationListSize();
        assertEquals(beforeSize + 1, sizeAfterAddition);
    }
    
}
