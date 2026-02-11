package com.app.feature.messaging.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.feature.auth.model.CustomUserDetails;
import com.app.feature.auth.model.User;
import com.app.feature.auth.service.UserService;
import com.app.feature.messaging.dto.createConversationDTO;
import com.app.feature.messaging.model.Conversation;
import com.app.feature.messaging.model.Message;
import com.app.feature.messaging.model.MessageType;
import com.app.feature.messaging.service.MessageService;
import com.app.feature.post.model.PostEntity;
import com.app.feature.post.service.PostService;

@RestController
@RequestMapping("/api/msg")
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;
    private final PostService postService;

    public MessageController(UserService userService, MessageService messageService, PostService postService) {
        this.userService = userService;
        this.messageService = messageService;
        this.postService = postService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Boolean>> sendMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody createConversationDTO request) {
        User user2 = userService.findById(request.getRecipientUuid());
        PostEntity post = postService.findById(request.getPost());
        if (user2 != null && post != null) {
            messageService.createConversation(userDetails.getUser(), user2, request.getMessage(), request.getType(),
                    post);
            return ResponseEntity.status(200).body(new ApiResponse<>(true, true, "success"));
        }
        return ResponseEntity.status(400).body(new ApiResponse<>(false, false, "failed"));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<Conversation>> getConversation(
            @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UUID user2, @RequestBody Long postID) {
        User recipientUser = userService.findById(user2);
        PostEntity post = postService.findById(postID);
        if (recipientUser != null && post != null) {
            Conversation conversation = messageService.findConversation(recipientUser, recipientUser, post);
            if (conversation != null) {
                return ResponseEntity.status(200).body(new ApiResponse<>(true, conversation, "success"));
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse<>(false, null, "failed"));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Conversation>>> getAllConversations(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<Conversation> conversations = messageService.getAllConversations(userDetails.getUser());
        if (conversations != null) {
            return ResponseEntity.status(200).body(new ApiResponse<>(true, conversations, "success"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, null, "failed"));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Boolean>> updateConversation(
            @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Long messageID,
            @RequestBody MessageType type) {
        Message message = messageService.getMessageById(messageID);
        if (message != null) {
            messageService.updateMessage(message, type);
            return ResponseEntity.status(200).body(new ApiResponse<>(true, true, "success"));
        } else {
            return ResponseEntity.status(200).body(new ApiResponse<>(true, false, "failed"));
        }
    }
}
