package com.app.feature.messaging.service;

import java.util.List;
import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.feature.auth.model.User;
import com.app.feature.messaging.model.Conversation;
import com.app.feature.messaging.model.Message;
import com.app.feature.messaging.model.MessageType;
import com.app.feature.messaging.repository.ConversationRepository;
import com.app.feature.messaging.repository.MessageRepository;
import com.app.feature.notifications.model.Notification;
import com.app.feature.notifications.model.NotificationType;
import com.app.feature.notifications.service.NotificationService;
import com.app.feature.post.model.PostEntity;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MessageService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public MessageService(ConversationRepository conversationRepository, MessageRepository messageRepository,
            SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void createConversation(User user1, User user2, String message, MessageType type, PostEntity post) {
        Message msg = new Message(user1, type, message);
        Optional<Conversation> searchConversation = conversationRepository.findByTwoUserAndPost(user1, user2,
                post);
        Message savedMsg = messageRepository.save(msg);
        if (searchConversation.isPresent()) {
            Conversation conversation = searchConversation.get();
            conversation.addMessage(savedMsg);
            conversation = conversationRepository.save(conversation);
            messagingTemplate.convertAndSend(
                    "/topic/msg/" + conversation.getId(),
                    conversation);
            Notification notification = new Notification(user1, user2, NotificationType.MESSAGE, savedMsg.getId());
            notificationService.sendNotifications(notification, user1, user2);
        } else {
            Conversation conversation = new Conversation(user1, user2, post);
            conversation.addMessage(savedMsg);
            conversation = conversationRepository.save(conversation);
            messagingTemplate.convertAndSend(
                    "/topic/msg/" + conversation.getId(),
                    conversation);
            Notification notification = new Notification(user1, user2, NotificationType.MESSAGE, savedMsg.getId());
            notificationService.sendNotifications(notification, user1, user2);
        }
    }

    public Conversation findConversation(User user1, User user2, PostEntity post) {
        Optional<Conversation> conversation = conversationRepository.findByTwoUserAndPost(user1, user2, post);
        if (conversation.isPresent()) {
            return conversation.get();
        }
        return null;
    }

    public List<Conversation> getAllConversations(User user) {
        List<Conversation> conversations = conversationRepository.findByUser(user);
        return conversations;
    }
}
