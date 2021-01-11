package org.gamsiz.chatapp.service;

import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.model.entity.Conversation;
import org.gamsiz.chatapp.repository.ConversationRepository;
import org.gamsiz.chatapp.util.ConversationTestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ConversationServiceImplTest {

    private final ConversationRepository conversationRepository = mock(ConversationRepository.class);
    private final  ConversationServiceImpl conversationService = new ConversationServiceImpl(conversationRepository);
    
    @Test
    public void shouldListConversationsSuccessfully() {
        Conversation conversation = ConversationTestUtil.createConversationFromUser1toUser2();
        given(conversationRepository.findAll()).willReturn(Arrays.asList(conversation));
        
        List<ConversationDTO> conversationDTOList = conversationService.listConversations(ConversationTestUtil.SAMPLE_USER_1);
        assertEquals(1, conversationDTOList.size());

        Conversation conversation2 = ConversationTestUtil.createConversationFromUser1toUser3();
        given(conversationRepository.findAll()).willReturn(Arrays.asList(conversation, conversation2));

        conversationDTOList = conversationService.listConversations(ConversationTestUtil.SAMPLE_USER_1);
        assertEquals(2, conversationDTOList.size());
    }

    @Test
    public void shouldGetConversationSuccessfully() {
        Conversation conversation = ConversationTestUtil.createConversationFromUser1toUser2();
        given(conversationRepository.findOne(Mockito.any(Example.class))).willReturn(Optional.of(conversation));

        Optional<ConversationDTO> conversation1 = conversationService.getConversation(
                ConversationDTO.builder()
                        .user(ConversationTestUtil.SAMPLE_USER_1)
                        .participant(ConversationTestUtil.SAMPLE_USER_2)
                        .build()
        );
        assertTrue(conversation1.isPresent());
    }
    
    @Test
    public void shouldCreateConversationSuccessfully() {
        Conversation conversation = ConversationTestUtil.createConversationFromUser1toUser2();
        given(conversationRepository.save(Mockito.any(Conversation.class))).willReturn(conversation);

        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        ConversationDTO created = conversationService.createConversation(conversationDTO);

        assertEquals(conversationDTO.getUser(), created.getUser());
        assertEquals(conversationDTO.getParticipant(), created.getParticipant());
    }

    @Test
    public void shouldDeleteConversationSuccessfully() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        conversationService.deleteConversation(conversationDTO);
    }

}
