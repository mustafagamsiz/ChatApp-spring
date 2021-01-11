package org.gamsiz.chatapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.gamsiz.chatapp.exception.NoSuchConversationException;
import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/chat/v1")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/{userId}/conversation")
    public List<ConversationDTO> listConversations(@Valid @PathVariable String userId) {
        List<ConversationDTO> conversations = conversationService.listConversations(userId);
        log.debug("Returning conversations result for user {}.", userId);
        return conversations;
    }

    @GetMapping("/{userId}/conversation/{participantId}")
    public ConversationDTO getConversation(@Valid @PathVariable String userId,
                                                 @Valid @PathVariable String participantId) {
        ConversationDTO conversationDTO = ConversationDTO.builder().user(userId).participant(participantId).build();
        Optional<ConversationDTO> retrieved = conversationService.getConversation(conversationDTO);

        retrieved.orElseThrow(
                () -> new NoSuchConversationException(conversationDTO.getParticipant()));

        log.debug("Returning conversation result for user {}.", userId);
        return retrieved.get();
    }

    @PostMapping("/{userId}/conversation")
    public ResponseEntity<ConversationDTO> createConversation(@Valid @PathVariable String userId,
                                                             @Valid @RequestBody ConversationDTO conversationDTO) {
        conversationDTO.setUser(userId);

        Optional<ConversationDTO> found = conversationService.getConversation(conversationDTO);
        if (found.isPresent()) {
            log.debug("Conversation user \"{}\" with participant \"{}\" is found in store, no need for creation.",
                    conversationDTO.getUser(),
                    conversationDTO.getParticipant());
            return ResponseEntity.status(HttpStatus.OK).body(found.get());
        }

        ConversationDTO created = conversationService.createConversation(conversationDTO);
        log.info("Create conversation operation has been executed successfully for conversation DTO: {}", conversationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{userId}/conversation/{participantId}")
    public ResponseEntity<Void> deleteConversation(@Valid @PathVariable String userId,
                                   @Valid @PathVariable String participantId) {
        ConversationDTO conversationDTO = ConversationDTO.builder().user(userId).participant(participantId).build();
        conversationService.deleteConversation(conversationDTO);
        log.info("Delete conversation operation has been executed successfully for conversation DTO: {}", conversationDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
