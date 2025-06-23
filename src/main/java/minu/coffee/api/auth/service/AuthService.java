package minu.coffee.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.auth.dto.LoginDto;
import minu.coffee.common.filter.exception.AppException;
import minu.coffee.common.filter.exception.ErrorCode;
import minu.coffee.common.model.TokenMember;
import minu.coffee.config.security.JwtUtil;
import minu.coffee.memberInfo.model.MemberInfo;
import minu.coffee.memberInfo.repository.MemberInfoRepo;
import minu.coffee.shopInfo.model.ShopInfo;
import minu.coffee.shopInfo.repository.ShopInfoRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final MemberInfoRepo memberInfoRepo;
    private final ShopInfoRepo shopInfoRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberInfo register(LoginDto.Register.Request param) {

        Boolean existed = memberInfoRepo.existsByMemberIdOrEmailOrPhoneNumber(param.getId(), param.getEmail(), param.getPhoneNumber());
        if (Boolean.TRUE.equals(existed)) {
            log.error("Register Fail: Already exist account info - memberId: {}, email: {}, phoneNumber: {}",
                    param.getId(), param.getEmail(), param.getPhoneNumber());
            throw new AppException(ErrorCode.AUTH002);
        }

        String hashed = passwordEncoder.encode(param.getPassword());

        ShopInfo shopInfo = shopInfoRepo.findById(param.getShopInfoId()).orElseThrow(
                () -> {
                    log.error("Register Fail: ShopInfo not found - shopInfoId: {}", param.getShopInfoId());
                    return new AppException(ErrorCode.DB001);
                }
        );

        MemberInfo member = MemberInfo.builder()
                .shopInfo(shopInfo)
                .memberId(param.getId())
                .memberPassword(hashed)
                .firstName(param.getFirstName())
                .lastName(param.getLastName())
                .email(param.getEmail())
                .phoneNumber(param.getPhoneNumber())
                .build();
        return memberInfoRepo.save(member);
    }

    @Transactional(readOnly = true)
    public LoginDto.Login.Response login(LoginDto.Login.Request param) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            param.getId(),
                            param.getPassword()
                    )
            );

            TokenMember tokenMember = (TokenMember) authentication.getPrincipal();
            String token = jwtUtil.generateToken(tokenMember.getTokenInfo());

            return new LoginDto.Login.Response(token);
        } catch (Exception e) {
            log.error("Login Fail: {}", e.getMessage());
            throw new AppException(ErrorCode.AUTH001);
        }
    }
}
