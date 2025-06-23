package minu.coffee.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minu.coffee.api.auth.service.AuthService;
import minu.coffee.auth.dto.LoginDto;
import minu.coffee.common.model.ApiResponse;
import minu.coffee.common.util.CommonUtil;
import org.springframework.http.ResponseEntity;
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
        return CommonUtil.convertResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto.Login.Request param) {

        LoginDto.Login.Response response = authService.login(param);
        return CommonUtil.convertResponse(response);

    }
}