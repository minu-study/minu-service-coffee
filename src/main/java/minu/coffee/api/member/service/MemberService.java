package minu.coffee.api.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.common.filter.customException.AppException;
import minu.coffee.common.filter.customException.ErrorCode;
import minu.coffee.common.model.TokenInfo;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.member.MemberDto;
import minu.coffee.memberInfo.model.MemberInfo;
import minu.coffee.memberInfo.repository.MemberInfoRepo;
import minu.coffee.pointHistory.model.PointHistory;
import minu.coffee.pointHistory.repository.PointHistoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberInfoRepo memberInfoRepo;
    private final PointHistoryRepo pointHistoryRepo;

    @Transactional
    public void accumulatePoints(MemberDto.AccumulatePoints.Request param) {

        TokenInfo tokenInfo = CommonUtil.getTokenInfo();
        MemberInfo memberInfo = memberInfoRepo.findById(tokenInfo.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        Integer originalPoint = memberInfo.getPoint() == null ? 0 : memberInfo.getPoint();
        Integer pointToAdd = param.getPoint();
        if (pointToAdd > 0) {
            memberInfo.setPoint(originalPoint + pointToAdd);
        } else {
            throw new AppException(ErrorCode.MEMBER001);
        }

        pointHistoryRepo.save(PointHistory.builder()
                .memberInfo(memberInfo)
                .pointAmount(originalPoint)
                .pointBalanceAfter(pointToAdd)
                .transactionType("ACCUMULATE")
                .build()
        );

    }

}
