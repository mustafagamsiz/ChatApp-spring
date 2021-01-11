package org.gamsiz.chatapp.repository;

import org.gamsiz.chatapp.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

}
