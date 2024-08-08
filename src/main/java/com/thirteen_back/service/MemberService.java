package com.thirteen_back.service;

import com.thirteen_back.constant.Authority;
import com.thirteen_back.dto.MemberReqDto;
import com.thirteen_back.dto.MemberResDto;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.thirteen_back.security.SecurityUtil.getCurrentMemberId;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member memberIdFindMember(){
        String id = getCurrentMemberId();
        log.info("userId:{}",String.valueOf(id));
        Member mem = new Member();
        Optional<Member> member = memberRepository.findByMid(id);
        if(member.isPresent()){
            mem = member.get();
        }
        return mem;
    }
    public boolean checkPw(MemberReqDto memberReqDto){
        String pw1 = memberReqDto.getPwd();
        String pw2 = memberIdFindMember().getPwd();
        Boolean istrue = passwordEncoder.matches(pw1,pw2);
        return istrue;
    }
    public boolean editInfo(String info, int type){
        Member member = memberIdFindMember();
        switch (type){
            case 1:
                member.setImage(info);
                memberRepository.save(member);
                return true;
            case 2:
                member.setPwd(passwordEncoder.encode(info));
                memberRepository.save(member);
                return true;
            case 3:
                member.setNick(info);
                memberRepository.save(member);
                return true;
            case 4:
                member.setEmail(info);
                memberRepository.save(member);
                return true;
            case 5:
                member.setPay(Long.parseLong(info));
                memberRepository.save(member);
                return true;
            default:
                return false;
        }
    }
    public boolean withdraw(){
        try {
            Member member = memberIdFindMember();
            memberRepository.delete(member);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<MemberResDto> allUsers(){
        List<MemberResDto> list = new ArrayList<>();
        try {
            List<Member> members = memberRepository.findAll();
            for (Member m : members) {
                if(m.getAuthority() == Authority.ROLL_USER){
                    list.add(MemberResDto.of(m));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}