package org.gamsiz.chatapp.controller;

import org.gamsiz.chatapp.exception.NoSuchConversationException;
import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.service.ConversationService;
import org.gamsiz.chatapp.util.ConversationTestUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ConversationControllerTest {

    private final ConversationService conversationService = mock(ConversationService.class);

    private final ConversationController conversationController = new ConversationController(conversationService);

    @Test
    public void shouldListConversationsSuccessfully() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        given(conversationService.listConversations(ConversationTestUtil.SAMPLE_USER_1)).willReturn(Arrays.asList(conversationDTO));

        List<ConversationDTO> conversations = conversationController.listConversations(ConversationTestUtil.SAMPLE_USER_1);
        assertEquals(1, conversations.size());
    }

    @Test
    public void shouldThrowNoSuchConversationExceptionForGetWithNonExistingParticipant() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();

        doThrow(new NoSuchConversationException(ConversationTestUtil.SAMPLE_USER_2))
                .when(conversationService).getConversation(conversationDTO);

        NoSuchConversationException exception = assertThrows(
                NoSuchConversationException.class,
                () -> conversationController.getConversation(
                        conversationDTO.getUser(), conversationDTO.getParticipant()
                )
        );

        assertTrue(exception.getMessage().contains(conversationDTO.getParticipant()));
    }

    @Test
    public void shouldCreateConversationSuccessfully() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        conversationController.createConversation(conversationDTO.getUser(), conversationDTO);
    }

    @Test
    public void shouldDeleteConversationSuccessfully() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        conversationController.deleteConversation(conversationDTO.getUser(), conversationDTO.getParticipant());
    }

    @Test
    public void shouldThrowNoSuchConversationExceptionForDeletionWithNonExistingSKU() {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();

        doThrow(new NoSuchConversationException(ConversationTestUtil.SAMPLE_USER_2))
                .when(conversationService).deleteConversation(conversationDTO);

        NoSuchConversationException exception = assertThrows(
                NoSuchConversationException.class,
                () -> conversationController.deleteConversation(
                        conversationDTO.getUser(), conversationDTO.getParticipant()
                )
        );

        assertTrue(exception.getMessage().contains(conversationDTO.getParticipant()));
    }

}
