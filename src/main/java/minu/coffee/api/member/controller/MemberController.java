package minu.coffee.api.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minu.coffee.api.member.service.MemberService;
import minu.coffee.common.model.ApiResponse;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.member.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/accumulate-points")
    public ResponseEntity<ApiResponse> accumulatePoints(@RequestBody @Valid MemberDto.AccumulatePoints.Request param) {
        memberService.accumulatePoints(param);
        return CommonUtil.convertResponse();
    }

}
