package com.thirteen_back.repository;

import com.thirteen_back.entity.Board;
import com.thirteen_back.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);
    Page<Comment> findByBoard(Board board, Pageable pageable);
    Optional<Comment> findByCno(Long cno);
    List<Comment> findByTextContaining(String text);
//    Optional<Comment> findByMno(Long mno);
}
