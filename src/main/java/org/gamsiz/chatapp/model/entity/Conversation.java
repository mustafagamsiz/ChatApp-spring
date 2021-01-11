package org.gamsiz.chatapp.model.entity;

import lombok.*;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Conversation extends BaseEntity {

    private String user;
    private String participant;
    @Builder.Default
    private long lastMessageId = -1;

    public Conversation(String user, String participant) {
        this.user = user;
        this.participant = participant;
    }

}
