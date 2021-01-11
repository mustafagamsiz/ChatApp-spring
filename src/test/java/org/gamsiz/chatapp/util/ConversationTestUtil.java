package org.gamsiz.chatapp.util;

import lombok.experimental.UtilityClass;
import org.gamsiz.chatapp.model.dto.ConversationDTO;
import org.gamsiz.chatapp.model.entity.Conversation;

@UtilityClass
public class ConversationTestUtil {

    public static final String SAMPLE_USER_1 = "user1@chat.app";
    public static final String SAMPLE_USER_2 = "user2@chat.app";
    public static final String SAMPLE_USER_3 = "user3@chat.app";

    public static ConversationDTO createConversationDTOFromUser1toUser2() {
        return ConversationDTO.builder()
                .user(SAMPLE_USER_1)
                .participant(SAMPLE_USER_2)
                .build();
    }

    public static ConversationDTO createConversationDTOFromUser1toUser3() {
        return ConversationDTO.builder()
                .user(SAMPLE_USER_1)
                .participant(SAMPLE_USER_3)
                .build();
    }

    public static ConversationDTO createConversationDTOFromUser2toUser3() {
        return ConversationDTO.builder()
                .user(SAMPLE_USER_2)
                .participant(SAMPLE_USER_3)
                .build();
    }

    public static Conversation createConversationFromUser1toUser2() {
        return new Conversation(SAMPLE_USER_1, SAMPLE_USER_2);
    }

    public static Conversation createConversationFromUser1toUser3() {
        return new Conversation(SAMPLE_USER_1, SAMPLE_USER_3);
    }

    public static Conversation createConversationFromUser2toUser3() {
        return new Conversation(SAMPLE_USER_2, SAMPLE_USER_3);
    }
}
