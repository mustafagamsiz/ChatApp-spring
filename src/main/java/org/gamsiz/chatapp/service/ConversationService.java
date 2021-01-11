package org.gamsiz.chatapp.service;

import org.gamsiz.chatapp.model.dto.ConversationDTO;

import java.util.List;
import java.util.Optional;

public interface ConversationService {

    List<ConversationDTO> listConversations(String userId);

    Optional<ConversationDTO> getConversation(ConversationDTO conversationDTO);

    ConversationDTO createConversation(ConversationDTO conversationDTO);

    void deleteConversation(ConversationDTO conversationDTO);

}
