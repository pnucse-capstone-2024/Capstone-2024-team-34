package com.web.cartalk.emailVerification.controller;

import com.web.cartalk.core.utils.ApiUtils;
import com.web.cartalk.emailVerification.dto.EmailVerificationRequest;
import com.web.cartalk.emailVerification.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmailVerificationController {
    final private EmailVerificationService emailVerificationService;

    @PostMapping("/email-verifications/{id}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable Long id) {
        emailVerificationService.sendVerificationCode(id);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/email-verifications/{id}/confirm")
    public ResponseEntity<?> verifyVerificationCode(@PathVariable Long id, @RequestBody @Valid EmailVerificationRequest.VerificationCodeDto requestDto) {
        emailVerificationService.verifyVerificationCode(id, requestDto);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok().body(response);
    }
}
