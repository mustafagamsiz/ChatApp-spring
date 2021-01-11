package org.gamsiz.chatapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.model.entity.Conversation;
import org.gamsiz.chatapp.repository.ConversationRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Override
    public List<ConversationDTO> listConversations(String userId) {
        log.debug("Conversations are requested for the user : {}", userId);
        return conversationRepository.findAll().stream().map(ConversationDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ConversationDTO> getConversation(ConversationDTO conversationDTO) {
        log.debug("Conversation of user \"{}\" with participant \"{}\" is requested.",
                conversationDTO.getUser(),
                conversationDTO.getParticipant());
        return retrieveIfConversationExists(conversationDTO);
    }

    @Override
    public ConversationDTO createConversation(ConversationDTO conversationDTO) {
        log.debug("Conversation user \"{}\" with participant \"{}\" is requested to be created.",
                conversationDTO.getUser(),
                conversationDTO.getParticipant());

        return new ConversationDTO(conversationRepository.save(
                convertDTOtoEntity(conversationDTO)
        ));
    }

    @Override
    public void deleteConversation(ConversationDTO conversationDTO) {
        conversationRepository.delete(convertDTOtoEntity(conversationDTO));
    }

    private Conversation convertDTOtoEntity(ConversationDTO conversationDTO) {
        return Conversation.builder()
                .user(conversationDTO.getUser())
                .participant(conversationDTO.getParticipant())
                .build();
    }

    private Optional<ConversationDTO> retrieveIfConversationExists(final ConversationDTO conversationDTO) {
        return conversationRepository.findOne(
                Example.of(
                        convertDTOtoEntity(conversationDTO)
                )
        ).stream().map(ConversationDTO::new).findFirst();
    }
}
