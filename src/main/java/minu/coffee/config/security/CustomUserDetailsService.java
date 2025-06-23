package minu.coffee.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.common.filter.exception.AppException;
import minu.coffee.common.filter.exception.ErrorCode;
import minu.coffee.common.model.TokenInfo;
import minu.coffee.common.model.TokenMember;
import minu.coffee.memberInfo.model.MemberInfo;
import minu.coffee.memberInfo.repository.MemberInfoRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberInfoRepo memberInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String memberId)
            throws UsernameNotFoundException {

        MemberInfo member = memberInfoRepo.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // MemberInfo -> TokenInfo 변환
        TokenInfo tokenInfo = convertToTokenInfo(member);

        // TokenInfo -> TokenMember 변환
        return new TokenMember(tokenInfo);
    }

    private TokenInfo convertToTokenInfo(MemberInfo member) {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(member.getId());
        tokenInfo.setLastName(member.getLastName());
        tokenInfo.setFirstName(member.getFirstName());
        tokenInfo.setMemberId(member.getMemberId());
        tokenInfo.setMemberPassword(member.getMemberPassword());
        tokenInfo.setPhoneNumber(member.getPhoneNumber());

        // ShopInfo가 있는 경우 ID 추출
        if (member.getShopInfo() != null) {
            tokenInfo.setShopInfoId(member.getShopInfo().getId());
        } else {
            log.warn("Shop info is null");
            throw new AppException(ErrorCode.AUTH001);
        }

        // 권한 설정 (예시: 관리자 여부)
        tokenInfo.setIsAdmin(isAdminUser(member));
        return tokenInfo;
    }

    private Boolean isAdminUser(MemberInfo member) {
        return member.getEmail().endsWith("@admin.com");
    }
}