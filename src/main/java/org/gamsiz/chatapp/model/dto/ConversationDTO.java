package org.gamsiz.chatapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gamsiz.chatapp.model.entity.Conversation;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDTO {

    private String user;
    @NotBlank(message = "Conversation's participant can not be blank")
    private String participant;

    public ConversationDTO(Conversation conversation) {
        this.user = conversation.getUser();
        this.participant = conversation.getParticipant();
    }

}
