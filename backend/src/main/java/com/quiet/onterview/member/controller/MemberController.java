package com.quiet.onterview.member.controller;

import com.quiet.onterview.member.dto.MemberDuplicateResponse;
import com.quiet.onterview.member.dto.MemberLoginRequest;
import com.quiet.onterview.member.dto.MemberLoginResponse;
import com.quiet.onterview.member.dto.MemberSignupRequest;
import com.quiet.onterview.member.dto.MemberModifyPasswordRequest;
import com.quiet.onterview.member.dto.MemberTokenResponse;
import com.quiet.onterview.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity signUpByEmail(@RequestBody MemberSignupRequest memberSignupRequest) throws Exception {
        memberService.signUpByEmail(memberSignupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest memberLoginRequest) throws Exception {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PatchMapping("/password")
    public ResponseEntity modifyPassword(@RequestHeader("Authorization") String accessToken,
            @RequestBody MemberModifyPasswordRequest memberModifyPasswordRequest) throws Exception {
        memberService.modifyPassword(accessToken, memberModifyPasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity withdrawMember(@RequestHeader("Authorization") String accessToken) throws Exception {
        memberService.withdrawUser(accessToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<MemberDuplicateResponse> checkNicknameAvailable(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(MemberDuplicateResponse.builder().isAvaliable(memberService.isNicknameAvailable(nickname)).build());
    }

    @GetMapping("/check/email")
    public ResponseEntity<MemberDuplicateResponse> checkEmailAvailable(@RequestParam("email") String email) {
        return ResponseEntity.ok(MemberDuplicateResponse.builder().isAvaliable(memberService.isEmailAvailable(email)).build());
    }

    @GetMapping("/token")
    public ResponseEntity<MemberTokenResponse> remakeMemberToken(@RequestHeader("AccessToken") String accessToken,
            @RequestHeader("RefreshToken") String refreshToken) throws Exception {
        return ResponseEntity.ok(memberService.remakeMemberToken(accessToken, refreshToken));
    }
}
