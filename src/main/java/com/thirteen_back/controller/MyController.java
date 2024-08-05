package com.thirteen_back.controller;

import com.thirteen_back.dto.MemberReqDto;
import com.thirteen_back.dto.MemberResDto;
import com.thirteen_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    public final MemberService memberService;

    @GetMapping("/detail")
    public ResponseEntity<MemberResDto> memberDetail() {
        MemberResDto memberDto = MemberResDto.of(memberService.memberIdFindMember());
        return ResponseEntity.ok(memberDto);
    }
    @GetMapping("/authority")
    public ResponseEntity<String> memberAuthority() {
        String authority = memberService.memberIdFindMember().getAuthority().toString();
        return ResponseEntity.ok(authority);
    }
    @PostMapping("/checkpw")
    public ResponseEntity<Boolean> chechPw (@RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(memberService.checkPw(memberReqDto));
    }
    @PostMapping("/editinfo")
    public ResponseEntity<Boolean> editInfo (@RequestBody Map<String,String> data){
        String info = data.get("info");
        int type = Integer.parseInt(data.get("type"));
        return ResponseEntity.ok(memberService.editInfo(info,type));}
    @GetMapping("withdraw")
    public ResponseEntity<Boolean> withdraw () {
        return ResponseEntity.ok(memberService.withdraw());
    }
}