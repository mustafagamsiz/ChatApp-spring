package org.gamsiz.chatapp.model.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {

    private final long id;
    private final long conversationId;
    private final String from;
    private final String to;
    private final String contentType;
    private final String content;
    private final long timestamp;
}
