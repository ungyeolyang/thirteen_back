package com.thirteen_back.repository;

import com.thirteen_back.constant.Category;
import com.thirteen_back.entity.Board;
import com.thirteen_back.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBno(Long bno);
    Page<Board> findByCategory(Category category, Pageable pageable);
    Page<Board> findByTitleContaining(String title, Pageable pageable);
    Page<Board> findByTitleContainingAndCategory(String title, Category category, Pageable pageable);
    List<Board> findByMember(Member member);
    List<Board> findByWarningGreaterThanEqual(int warning);
}
