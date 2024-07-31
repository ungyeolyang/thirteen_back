package com.thirteen_back.repository;

import com.thirteen_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email); //optional null 방지
    Optional<Member> findByMid(String mid); //optional null 방지
    Optional<Member> findByNick(String nick); //optional null 방지
    @Query(value = "SELECT * FROM member WHERE mid != :id", nativeQuery = true)
    List<Member> findAllExceptMine(@Param("id") String id);
    Optional<Member> findByEmailAndPwd(String email, String pwd);
    boolean existsByEmail(String email);
    boolean existsByNick(String nick);
    boolean existsByMid(String id);
    boolean existsByRefreshToken(String refreshToken);
}