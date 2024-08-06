package com.thirteen_back.service;

import com.thirteen_back.constant.Category;
import com.thirteen_back.dto.BoardDto;
import com.thirteen_back.entity.Board;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.BoardRepository;
import com.thirteen_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

// 게시글 생성 & 저장
    public boolean saveBoard(BoardDto boardDto) {
        Member member = memberRepository.findById(boardDto.getMno())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .bdate(boardDto.getBdate())
                .category(Category.valueOf(boardDto.getCategory()))
                .member(member)
                .warning(boardDto.getWarning())
                .build();
        boardRepository.save(board);
        return true;
    }
// 게시글 삭제
    public boolean deleteBoard(Long bno) {
        try {
            Board board = boardRepository.findById(bno).orElseThrow(
                    () -> new RuntimeException("해당 댓글이 존재하지 않습니다.")
            );
            boardRepository.delete(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
// 게시글 수정
    public boolean updateBoard(BoardDto dto) {
        log.warn("updateBoard 실행!");
        Board board = boardRepository.findById(dto.getBno()).orElseThrow(
                () -> new RuntimeException("해당 글이 존재하지 않습니다."));
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setWarning(dto.getWarning());
        board.setBdate(LocalDateTime.now());
        board.setCategory(Category.valueOf(dto.getCategory()));
        boardRepository.save(board);
        return true;
    }
// 게시판 목록 보여주기
    public List<BoardDto> boardList() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
// 내가 쓴 게시글 보여주기
    public List<BoardDto> myBoardList(Long mno) {
        Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        List<Board> boards = boardRepository.findByMember(member);
        return boards.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private BoardDto convertEntityToDto(Board board) {
        return BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .bdate(board.getBdate())
                .warning(board.getWarning())
                .category(String.valueOf(board.getCategory()))
                .mno(board.getMember().getMno())
                .build();
    }
}
