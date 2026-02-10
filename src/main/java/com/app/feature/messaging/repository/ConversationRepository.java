package com.app.feature.messaging.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.feature.auth.model.User;
import com.app.feature.messaging.model.Conversation;
import com.app.feature.post.model.PostEntity;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.post = :post AND ((c.user1 = :user1 AND c.user2 = :user2) OR (c.user1 = :user2 AND c.user2 = :user1))")
    Optional<Conversation> findByTwoUserAndPost(@Param("user1") User user1, @Param("user2") User user2,
            @Param("post") PostEntity post);

    @Query("SELECT c FROM Conversation c WHERE (c.user1 = :user OR c.user2 = :user)")
    List<Conversation> findByUser(@Param("user") User user);
}
