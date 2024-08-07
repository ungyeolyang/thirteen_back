package com.thirteen_back.repository;

import com.thirteen_back.constant.BoardCategory;
import com.thirteen_back.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByCategory(BoardCategory boardCategory);
}