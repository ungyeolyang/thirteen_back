package com.thirteen_back.service;

import com.thirteen_back.dto.CommentDto;
import com.thirteen_back.entity.Board;
import com.thirteen_back.entity.Comment;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.BoardRepository;
import com.thirteen_back.repository.CommentRepository;
import com.thirteen_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
//    private final MemberRepository memberRepository;
//    private final BoardRepository boardRepository;
//    private final CommentRepository commentRepository;
//
//    // 댓글 등록
//    @Transactional
//    public boolean saveComment (CommentDto commentDto) {
//        try {
////            Member member = memberRepository.findById(commentDto.getMno()).orElseThrow(
////                    () -> new RuntimeException("존재하지 않는 회원입니다.")
////            );
//            Board board = boardRepository.findByBno(commentDto.getBno()).orElseThrow(
//                    () -> new RuntimeException("존재하지 않는 게시글입니다.")
//            );
//            Comment comment = Comment.builder()
////                    .member(member)
//                    .board(board)
//                    .text(commentDto.getText())
//                    .cdate(LocalDateTime.now())
//                    .warning(commentDto.getWarning())
//                    .build();
//            commentRepository.save(comment);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 댓글 수정
//    @Transactional
//    public boolean commentModify (CommentDto commentDto) {
//        try {
//            Comment comment = commentRepository.findById(commentDto.getCno()).orElseThrow(
//                    () -> new RuntimeException("존재하지 않는 댓글입니다.")
//            );
//            comment.setText(commentDto.getText());
//            commentRepository.save(comment);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 댓글 삭제
//    public boolean commentDelete(Long cno) {
//        try {
//            Comment comment = commentRepository.findById(cno).orElseThrow(
//                    () -> new RuntimeException("해당 댓글이 존재하지 않습니다.")
//            );
//            commentRepository.delete(comment);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 댓글 목록
//    public List<CommentDto> getBoardCommentList (Long bno) {
//        Board board = boardRepository.findById(bno)
//                .orElseThrow(()-> new RuntimeException("존재하지 않는 게시글입니다."));
//        List<Comment> comments = commentRepository.findByBoard(board);
//        List<CommentDto> commentDtos = new ArrayList<>();
//        for (Comment comment : comments) {
//            commentDtos.add(convertEntityToDto(comment));
//        }
//        return commentDtos;
//    }
//    // 댓글 엔티티를 Dto로 변환
//    public CommentDto convertEntityToDto (Comment comment) {
//        return CommentDto.builder()
//                .cno(comment.getCno())
//                .bno(comment.getBoard().getBno())
////                .mno(comment.getMember().getMno())
//                .text(comment.getText())
//                .cdate(comment.getCdate())
//                .warning(comment.getWarning())
//                .build();
//    }
}
