package org.gamsiz.chatapp.controller;

import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.util.AbstractIntegrationTest;
import org.gamsiz.chatapp.util.ConversationTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConversationControllerIT extends AbstractIntegrationTest {
    
    private static final String BASE_CONVERSATION_URL_TEMPLATE = "/api/chat/v1/{0}/conversation";
    private static final String SINGLE_CONVERSATION_URL_TEMPLATE = "/api/chat/v1/{0}/conversation/{1}";

    @Test
    public void shouldListConversationsSuccessfully() throws Exception {
        this.mockMvc.perform(
                get(MessageFormat.format(BASE_CONVERSATION_URL_TEMPLATE, ConversationTestUtil.SAMPLE_USER_1))
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldCreateConversationSuccessfully() throws Exception {
        ConversationDTO conversationDTO = ConversationTestUtil.createConversationDTOFromUser1toUser2();
        String json = this.objectMapper.writeValueAsString(conversationDTO);

        this.mockMvc.perform(post(MessageFormat.format(BASE_CONVERSATION_URL_TEMPLATE, ConversationTestUtil.SAMPLE_USER_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                    .andExpect(status().isCreated());

        this.mockMvc.perform(
                get(
                        MessageFormat.format(SINGLE_CONVERSATION_URL_TEMPLATE,
                                ConversationTestUtil.SAMPLE_USER_1, ConversationTestUtil.SAMPLE_USER_2)
                )
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteConversationSuccessfully() throws Exception {
        this.mockMvc.perform(
                delete(
                        MessageFormat.format(SINGLE_CONVERSATION_URL_TEMPLATE,
                                ConversationTestUtil.SAMPLE_USER_1, ConversationTestUtil.SAMPLE_USER_2)
                )
        ).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnHttpNotFoundForNonExistingConversation() throws Exception {
        this.mockMvc.perform(
                get(
                        MessageFormat.format(SINGLE_CONVERSATION_URL_TEMPLATE,
                                ConversationTestUtil.SAMPLE_USER_1, ConversationTestUtil.SAMPLE_USER_3)
                )
        ).andExpect(status().isNotFound());
    }
}
