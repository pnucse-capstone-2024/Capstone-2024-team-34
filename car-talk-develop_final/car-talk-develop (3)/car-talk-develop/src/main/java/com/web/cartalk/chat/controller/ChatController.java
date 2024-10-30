package com.web.cartalk.chat.controller;

import com.web.cartalk.chat.dto.ChatRequest;
import com.web.cartalk.chat.dto.ChatroomRequest;
import com.web.cartalk.chat.dto.ChatroomResponse;
import com.web.cartalk.chat.service.ChatService;
import com.web.cartalk.core.security.CustomUserDetails;
import com.web.cartalk.core.utils.ApiUtils;
import com.web.cartalk.core.utils.cursor.CursorRequest;
import com.web.cartalk.core.utils.cursor.PageCursor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/chatroom")
    public ResponseEntity<?> create(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ChatroomResponse.CreateChatroomDto createChatroomDto = chatService.create(userDetails.getId());
        ApiUtils.Response<?> response = ApiUtils.success(createChatroomDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chatroom/search")
    public ResponseEntity<?> get(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ChatroomResponse.GetChatroomDto getChatroomDto = chatService.get(userDetails.getId());
        ApiUtils.Response<?> response = ApiUtils.success(getChatroomDto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/chatroom/{chatroomId}")
    public ResponseEntity<?> changeTitle(@PathVariable Long chatroomId,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody @Valid ChatroomRequest.ChangeTitleDto requestDto,
                                         Errors errors) {
        chatService.changeTitle(chatroomId, userDetails.getId(), requestDto);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/chatroom/{chatroomId}")
    public ResponseEntity<?> deleteChatroom(@PathVariable Long chatroomId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatService.delete(userDetails.getId(), chatroomId);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{chatroomId}/question")
    public ResponseEntity<?> question(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long chatroomId, @RequestBody @Valid ChatRequest.question question) {
        return ResponseEntity.ok(ApiUtils.success(chatService.question(userDetails.getId(), chatroomId, question)));
    }

    @GetMapping("/chatroom/{chatroomId}/messages")
    public ResponseEntity<?> chatHistory(@PathVariable Long chatroomId,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         CursorRequest cursorRequest) {
        PageCursor<?> responseDto = chatService.chatHistory(userDetails.getId(), chatroomId, cursorRequest);
        ApiUtils.Response<?> response = ApiUtils.success(responseDto);
        return ResponseEntity.ok(response);
    }
}
