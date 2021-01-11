package org.gamsiz.chatapp.repository;

import org.gamsiz.chatapp.model.entity.Conversation;
import org.gamsiz.chatapp.util.ConversationTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
public class ConversationRepositoryIT {

    @Autowired
    private ConversationRepository conversationRepository;
    
    @Test
    public void shouldRetrieveConversationsSuccessfullyAfterCreation() {
        assertTrue(conversationRepository.findAll().isEmpty());
        
        Conversation conversation = ConversationTestUtil.createConversationFromUser1toUser2();
        conversationRepository.save(conversation);
        
        assertFalse(conversationRepository.findAll().isEmpty());
        assertTrue(conversationRepository.findAll().size() == 1);
    }
    
}
