package com.app.feature.notifications.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.feature.auth.model.User;
import com.app.feature.notifications.model.Notification;
import com.app.feature.notifications.model.NotificationType;
import com.app.feature.notifications.repository.NotificationRepository;
import com.app.feature.post.model.PostEntity;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void updateLikes(PostEntity post, User sender, boolean isSame, boolean isLike) {
        messagingTemplate.convertAndSend("/topic/post/" + post.getId(), post);
        if (!isSame && isLike) {
            Notification notification = new Notification(sender, post.getUser(), NotificationType.LIKE, post.getId());
            this.sendNotifications(notification, sender, post.getUser());
        }
    }

    public void sendNotifications(Notification notification, User sender, User recipient) {
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/user/" + recipient.getUuid(), notification);
    }

    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findAllByRecipientAndTypeNotOrderByCreationDateDesc(user,
                NotificationType.COMMENT);
    }
}
