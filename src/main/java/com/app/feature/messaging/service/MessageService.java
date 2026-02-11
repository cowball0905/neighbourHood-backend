package com.app.feature.messaging.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.app.feature.post.service.PostService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MessageService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final PostService postService;
    private final NotificationService notificationService;

    public MessageService(ConversationRepository conversationRepository, MessageRepository messageRepository,
            SimpMessagingTemplate messagingTemplate, NotificationService notificationService, PostService postService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.postService = postService;
        this.notificationService = notificationService;
    }

    public void createConversation(User user1, User user2, String message, MessageType type, PostEntity post) {
        Message msg = new Message(user1, type, message);
        Optional<Conversation> searchConversation = conversationRepository.findByTwoUserAndPost(user1, user2,
                post);
        Conversation conversation;
        if (searchConversation.isPresent()) {
            conversation = searchConversation.get();
        } else {
            conversation = new Conversation(user1, user2, post);
        }
        msg.setConversation(conversation);
        conversation.addMessage(msg);
        conversation = conversationRepository.save(conversation);
        msg = messageRepository.save(conversation.getMessages().get(conversation.getMessages().size() - 1));
        Notification notification = new Notification(user1, user2, NotificationType.MESSAGE, msg.getId());
        notificationService.sendNotifications(notification, user1, user2);
        if (type == MessageType.ACCEPT) {
            postService.acceptRequest(conversation.getPost().getId(), user2.getUuid());
        }
        messagingTemplate.convertAndSend(
                "/topic/msg/" + conversation.getId(),
                conversation);
    }

    public Conversation findConversation(User user1, User user2, PostEntity post) {
        Optional<Conversation> conversation = conversationRepository.findByTwoUserAndPost(user1, user2, post);
        if (conversation.isPresent()) {
            return conversation.get();
        }
        return null;
    }

    public void updateMessage(Message message, MessageType type) {
        message.setType(type);
        messageRepository.save(message);
        Optional<Conversation> conversation = conversationRepository.findById(message.getConversation().getId());
        if (conversation.isPresent()) {
            Conversation loopConversation = conversation.get();
            for (int i = 0; i < loopConversation.getMessages().size(); i++) {
                if (loopConversation.getMessages().get(i).getId() == message.getId()) {
                    loopConversation.getMessages().set(i, message);
                    conversationRepository.save(loopConversation);
                }
            }
        }
    }

    public void updateMessageRead(Message message) {
        message.setRead(true);
        messageRepository.save(message);
        Optional<Conversation> conversation = conversationRepository.findById(message.getConversation().getId());
        if (conversation.isPresent()) {
            Conversation loopConversation = conversation.get();
            for (int i = 0; i < loopConversation.getMessages().size(); i++) {
                if (loopConversation.getMessages().get(i).getId() == message.getId()) {
                    loopConversation.getMessages().set(i, message);
                    conversationRepository.save(loopConversation);
                }
            }
        }
    }

    public List<Conversation> getAllConversations(User user) {
        List<Conversation> conversations = conversationRepository.findByUser(user);
        return conversations;
    }

    public Message getMessageById(Long id) {
        Optional<Message> msg = messageRepository.findById(id);
        if (msg.isPresent()) {
            return msg.get();
        }
        return null;
    }
}
