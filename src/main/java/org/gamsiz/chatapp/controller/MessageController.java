package org.gamsiz.chatapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/chat/v1")
public class MessageController {

    private final MessageSenderService messageSenderService;

    private final MessageReceiverService messageReceiverService;

    private final MessageHistoryService messageHistoryService;

    public MessageController(MessageSenderService messageSenderService,
                             MessageReceiverService messageReceiverService,
                             MessageHistoryService messageHistoryService) {
        this.messageSenderService = messageSenderService;
        this.messageReceiverService = messageReceiverService;
        this.messageHistoryService = messageHistoryService;
    }

    @GetMapping("/{userId}/conversation/{participantId}/message")
    public List<MessageDTO> listMessages(@Valid @PathVariable String userId,
                                         @Valid @PathVariable String participantId) {
        List<MessageDTO> messages = messageHistoryService.listMessages(userId, participantId);
        log.debug("Returning messages result for user {}'s conversation with {}.", userId, participantId);
        return messages;
    }

    @PostMapping("/{userId}/conversation/{participantId}/message")
    public ResponseEntity<MessageDTO> sendMessage(@Valid @PathVariable String userId,
                                                  @Valid @PathVariable String participantId,
                                                  @Valid @RequestBody MessageDTO messageDTO) {
        messageDTO.setFrom(userId);
        messageDTO.setTo(participantId);

        MessageDTO sent = messageSenderService.sendMessage(messageDTO);
        log.info("Send message has been executed successfully for message DTO: {}", MessageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sent);
    }

    @PostMapping("/{userId}/conversation/{participantId}/message")
    public ResponseEntity<MessageFlagDTO> sendMessage(@Valid @PathVariable String userId,
                                                  @Valid @PathVariable String participantId,
                                                  @Valid @RequestBody MessageFlagDTO messageDTO) {
        messageDTO.setFrom(userId);
        messageDTO.setTo(participantId);

        MessageDTO sent = messageSenderService.sendMessage(messageDTO);
        log.info("Send message has been executed successfully for message DTO: {}", MessageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sent);
    }

    @DeleteMapping("/{userId}/conversation/{participantId}")
    public ResponseEntity<Void> deleteConversation(@Valid @PathVariable String userId,
                                                   @Valid @PathVariable String participantId) {
        ConversationDTO conversationDTO = ConversationDTO.builder().user(userId).participant(participantId).build();
        messageSenderService.deleteConversation(conversationDTO);
        log.info("Delete conversation operation has been executed successfully for conversation DTO: {}", conversationDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
