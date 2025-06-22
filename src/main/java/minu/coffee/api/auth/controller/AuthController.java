package minu.coffee.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minu.coffee.api.auth.service.AuthService;
import minu.coffee.auth.dto.LoginDto;
import minu.coffee.common.filter.Exception.AppException;
import minu.coffee.common.filter.Exception.ErrorCode;
import minu.coffee.common.model.ApiResponse;
import minu.coffee.common.model.TokenMember;
import minu.coffee.common.util.ResponseUtil;
import minu.coffee.config.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid LoginDto.Register.Request param) {
        authService.register(param);
        return ResponseUtil.ConvertResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto.Login.Request param) {

        LoginDto.Login.Response response = authService.login(param);
        return ResponseUtil.ConvertResponse(response);

    }
}