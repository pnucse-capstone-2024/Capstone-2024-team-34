package com.web.cartalk.user.controller;

import com.web.cartalk.core.errors.exception.Exception400;
import com.web.cartalk.core.errors.exception.Exception403;
import com.web.cartalk.core.security.CustomUserDetails;
import com.web.cartalk.core.security.JwtProvider;
import com.web.cartalk.core.utils.ApiUtils;
import com.web.cartalk.core.utils.Utils;
import com.web.cartalk.user.dto.UserRequest;
import com.web.cartalk.user.dto.UserResponse;
import com.web.cartalk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/my-info")
    public ResponseEntity<?> myInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(ApiUtils.success(userService.getUser(userDetails.getId())));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDto requestDto) {
        validateBirthForm(requestDto.birth());

        var responseDto = userService.join(requestDto);
        return ResponseEntity.ok()
                .body(ApiUtils.success(responseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDto requestDto) {
        var tokensDto = userService.issueJwtByLogin(requestDto);

        var responseCookie = createRefreshTokenCookie(tokensDto.refresh(), JwtProvider.REFRESH_EXP_SEC);

        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok().header(JwtProvider.HEADER, tokensDto.access())
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> reIssueTokens(@CookieValue("refreshToken") String refreshToken) {
        var tokensDto = userService.reIssueTokens(refreshToken);

        var responseCookie = createRefreshTokenCookie(tokensDto.refresh(), JwtProvider.REFRESH_EXP_SEC);

        var response = ApiUtils.success();
        return ResponseEntity.ok().header(JwtProvider.HEADER, tokensDto.access())
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.logout(userDetails.getId());

        var responseCookie = createRefreshTokenCookie("", 0);

        var response = ApiUtils.success();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    private static ResponseCookie createRefreshTokenCookie(String refreshToken, int maxAge) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true) // javascript 접근 방지
                .secure(true) // https 통신 강제
                .sameSite("None")
                .maxAge(maxAge)
                .build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody @Valid UserRequest.UpdateDto requestDto,
                                        Errors errors) {
        if (!Objects.equals(userDetails.getId(), id)) {
            throw new Exception403("권한이 없습니다.");
        }

        validateBirthForm(requestDto.birth());

        String jwt = userService.updateUser(id, requestDto);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (!Objects.equals(userDetails.getId(), id)) {
            throw new Exception403("권한이 없습니다.");
        }

        userService.deleteUser(id);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/help/loginId")
    public ResponseEntity<?> findUserId(@RequestBody @Valid UserRequest.FindUserIdDto requestDto) {
        UserResponse.FindUserIdDto responseDto = userService.findUserId(requestDto);
        ApiUtils.Response<?> response = ApiUtils.success(responseDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/help/password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserRequest.ResetPasswordDto requestDto) {
        userService.resetPassword(requestDto);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/validate/loginId")
    public ResponseEntity<?> validateLoginId(@RequestBody @Valid UserRequest.ValidateLoginIdDto requestDto) {
        userService.validateLoginId(requestDto);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestBody @Valid UserRequest.ValidateEmailDto requestDto) {
        userService.validateEmail(requestDto);
        ApiUtils.Response<?> response = ApiUtils.success();
        return ResponseEntity.ok(response);
    }

    //이 부분 수정 필요
    private static void validateBirthForm(String birth) {
        if (birth != null) {
            List<Integer> birthSplit = Arrays.stream(birth.split("-"))
                    .map(Integer::parseInt)
                    .toList();
            if (!Utils.validateDayOfDateString(birthSplit.get(0), birthSplit.get(1), birthSplit.get(2)))
                throw new Exception400("올바른 날짜가 아닙니다.(형식: 0000-00-00)");
        }
    }
}
