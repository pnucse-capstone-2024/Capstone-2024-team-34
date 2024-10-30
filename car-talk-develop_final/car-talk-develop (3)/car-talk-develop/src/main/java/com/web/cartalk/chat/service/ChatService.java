package com.web.cartalk.chat.service;

import com.web.cartalk.chat.Chatroom;
import com.web.cartalk.chat.dto.ChatRequest;
import com.web.cartalk.chat.dto.ChatResponse;
import com.web.cartalk.chat.dto.ChatroomRequest;
import com.web.cartalk.chat.dto.ChatroomResponse;
import com.web.cartalk.chat.message.Message;
import com.web.cartalk.chat.message.repository.MessageRepository;
import com.web.cartalk.chat.repository.ChatroomRepository;
import com.web.cartalk.core.errors.exception.Exception403;
import com.web.cartalk.core.errors.exception.Exception404;
import com.web.cartalk.core.errors.exception.Exception500;
import com.web.cartalk.core.utils.Utils;
import com.web.cartalk.core.utils.cursor.CursorRequest;
import com.web.cartalk.core.utils.cursor.PageCursor;
import com.web.cartalk.llm.dto.LlmRequest;
import com.web.cartalk.llm.service.LlmService;
import com.web.cartalk.user.User;
import com.web.cartalk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    private final LlmService llmService;

    @Transactional
    public ChatroomResponse.CreateChatroomDto create(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception404("회원이 존재하지 않습니다"));

        String title = Utils.convertDateTimeToString(LocalDateTime.now()) + " 대화";

        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .user(user)
                .build();

        try {
            Chatroom createdChatroom = chatroomRepository.save(chatroom);
            return new ChatroomResponse.CreateChatroomDto(createdChatroom);

        } catch (Exception e) {
            throw new Exception500("채팅방 생성 중 오류가 발생하였습니다.");
        }
    }

    public ChatroomResponse.GetChatroomDto get(Long userId) {
        List<Chatroom> chatrooms = chatroomRepository.findAllByUserIdOrderByIdDesc(userId);

        return ChatroomResponse.GetChatroomDto.of(chatrooms);
    }

    @Transactional
    public void changeTitle(Long chatroomId, Long userId, ChatroomRequest.ChangeTitleDto requestDto) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow(() -> new Exception404("채팅방을 찾을 수 없습니다."));

        if (!Objects.equals(chatroom.getUser().getId(), userId)) throw new Exception403("현재 유저의 채팅방이 아닙니다.");

        chatroom.updateTitle(requestDto.title());
    }

    @Transactional
    public void delete(Long userId, Long chatroomId) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow(() -> new Exception404("채팅방을 찾을 수 없습니다."));

        if (!Objects.equals(userId, chatroom.getUser().getId())) throw new Exception403("현재 유저의 채팅방이 아닙니다.");

        messageRepository.deleteAllByChatroomId(chatroomId);
        chatroomRepository.delete(chatroom);
    }

    public PageCursor<ChatroomResponse.getMessagesDto> chatHistory(Long userId, Long chatroomId, CursorRequest cursorRequest) {

        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new Exception404("채팅방을 찾을 수 없습니다."));

        // 채팅방의 유저와 현재 접속한 유저가 같은지 확인합니다.
        if (!Objects.equals(chatroom.getUser().getId(), userId)) {
            throw new Exception403("현재 유저의 채팅방이 아닙니다.");
        }

        var messages = getMessages(chatroomId, cursorRequest);
        Collections.reverse(messages);

        var response = ChatroomResponse.getMessagesDto.of(messages);

        var nextKey = messages.stream()
                .mapToLong(Message::getId)
                .min().orElse(CursorRequest.NONE_KEY);

        return new PageCursor<>(cursorRequest.next(nextKey), response);
    }

    @Transactional
    public ChatResponse.Answer question(Long userId, Long chatroomId, ChatRequest.question question) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new Exception404("채팅방을 찾을 수 없습니다."));

        // 채팅방의 유저와 현재 접속한 유저가 같은지 확인합니다.
        if (!Objects.equals(chatroom.getUser().getId(), userId)) {
            throw new Exception403("현재 유저의 채팅방이 아닙니다.");
        }

        // 질문 저장
        var uuid = UUID.randomUUID().toString();
        var userQuestion = messageRepository.save(new Message(null, chatroom, false, question.question(), uuid));

        // 질의
        var answer = llmService.question(new LlmRequest.Question(question.question()));

        // 답변 저장
        var userAnswer = messageRepository.save(new Message(null, chatroom, true, answer.answer(), uuid));

        return new ChatResponse.Answer(chatroomId, userQuestion.getContent(), userQuestion.getCreatedAt(), userAnswer.getContent(), userAnswer.getCreatedAt(), userAnswer.getId());
    }

    private List<Message> getMessages(Long chatroomId, CursorRequest cursorRequest) {
        if (cursorRequest.hasSize()) {
            Pageable pageable = PageRequest.of(0, cursorRequest.size());

            if (cursorRequest.hasKey())
                return messageRepository.findAllByChatroomIdAndIdLessThanOrderByIdDesc(chatroomId, cursorRequest.key() + 1L, pageable);
            return messageRepository.findAllByChatroomIdOrderByIdDesc(chatroomId, pageable);
        }
        return messageRepository.findAllByChatroomIdOrderByIdDesc(chatroomId);
    }
}
