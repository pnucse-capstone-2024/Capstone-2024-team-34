package com.web.cartalk.chat.message.repository;

import com.web.cartalk.chat.message.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MessageRepository extends JpaRepository<Message, Long> {

    void deleteAllByChatroomId(Long chatroomId);

    List<Message> findTop38ByChatroomIdOrderByIdDesc(Long chatroomId);

    Optional<Message> findTop1ByChatroomIdOrderByIdDesc(Long chatroomId);

    List<Message> findAllByChatroomIdAndIdLessThanOrderByIdDesc(Long chatroomId, Long key, Pageable pageable);

    List<Message> findAllByChatroomIdOrderByIdDesc(Long chatroomId, Pageable pageable);

    List<Message> findAllByChatroomIdOrderByIdDesc(Long chatroomId);
}
