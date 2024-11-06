package com.web.cartalk.emailVerification.service;

import com.web.cartalk.core.errors.exception.Exception400;
import com.web.cartalk.core.errors.exception.Exception404;
import com.web.cartalk.core.errors.exception.Exception500;
import com.web.cartalk.core.utils.Utils;
import com.web.cartalk.emailVerification.EmailVerification;
import com.web.cartalk.emailVerification.dto.EmailVerificationRequest;
import com.web.cartalk.emailVerification.repository.EmailVerificationRepository;
import com.web.cartalk.user.Role;
import com.web.cartalk.user.User;
import com.web.cartalk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmailVerificationService {
    final private EmailVerificationRepository emailVerificationRepository;

    final private UserRepository userRepository;

    final private JavaMailSender javaMailSender;

    @Transactional
    public void sendVerificationCode(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception404("유저를 찾을 수 없음"));
        var email = user.getEmail();

        var existEmailCnt = emailVerificationRepository.countByEmailAndCreatedAtBetween(email, LocalDate.now().atStartOfDay(), LocalDateTime.now());

        if (existEmailCnt >= 5) throw new Exception400("일일 인증번호 메일 요청 가능 횟수는 5회입니다.");

        var verificationCode = makeCode();
        var emailVerification = EmailVerification.builder()
                .email(email)
                .verificationCode(verificationCode)
                .build();

        sendEmail(email, verificationCode);

        try {
            emailVerificationRepository.save(emailVerification);
        } catch (Exception e) {
            throw new Exception500("인증 번호 생성 중 오류가 발생했습니다.");
        }
    }

    private String makeCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(888888) + 111111);
    }

    private void sendEmail(String email, String code) {
        String subject = "CAR TALK 이메일 인증번호입니다.";
        String text = "이메일 인증을 위한 인증번호는 " + code + "입니다. </br>";

        Utils.sendEmail(javaMailSender, email, subject, text);
    }

    @Transactional
    public void verifyVerificationCode(Long userId, EmailVerificationRequest.VerificationCodeDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다."));
        var email = user.getEmail();

        var emailVerification = emailVerificationRepository.findFirstByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new Exception404("존재하지 않은 이메일 입니다."));

        if (!Objects.equals(emailVerification.getVerificationCode(), requestDto.verificationCode())) {
            throw new Exception400("인증코드가 일치하지 않습니다.");
        }

        user.updateRole(Role.ROLE_USER);
    }
}
