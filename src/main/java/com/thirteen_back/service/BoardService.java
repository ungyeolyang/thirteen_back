
package com.thirteen_back.service;

import com.thirteen_back.constant.Authority;
import com.thirteen_back.constant.BoardCategory;
import com.thirteen_back.constant.TF;
import com.thirteen_back.dto.BoardDto;
import com.thirteen_back.dto.CommentDto;
import com.thirteen_back.dto.MemberResDto;
import com.thirteen_back.entity.Board;
import com.thirteen_back.entity.Comment;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.BoardRepository;
import com.thirteen_back.repository.CommentRepository;
import com.thirteen_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    public BoardCategory reCate(String cate) {
        if (cate.equals("faq")) {
            return BoardCategory.BOARD_FAQ;
        } else if (cate.equals("gong")) {
            return BoardCategory.BOARD_GONG;
        } else {
            return BoardCategory.BOARD_MOUN;
        }
    }

    public boolean boardSave(BoardDto dto) {
        try {
            Member member = memberService.memberIdFindMember();
            BoardCategory boardCategory = reCate(dto.getCate());

            Board board = Board.builder()
                    .bdate(LocalDateTime.now())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .category(boardCategory)
                    .memberbno(member)
                    .tf(TF.FALSE)
                    .build();

            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> selectBoard(String cate, int page) {
        List<BoardDto> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, 5);
        Map<String, Object> result = new HashMap<>();
        int cnt;
        try {
            BoardCategory boardCategory = reCate(cate);
            List<Board> boards = boardRepository.findByCategory(boardCategory,pageable).getContent();
            cnt = boardRepository.findByCategory(boardCategory,pageable).getTotalPages();
            for (Board b : boards) {
                BoardDto dto = BoardDto.of(b);
                list.add(dto);
            }
            result.put("board",list);
            result.put("page",cnt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean memberComeBack(String mid, boolean tf) {
        try {
            TF t = tf ? TF.TRUE : TF.FALSE;
            Optional<Member> member = memberRepository.findByMid(mid);
            if (member.isPresent()) {
                Member m = member.get();
                m.setWithdrawal(t);
                memberRepository.save(m);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public BoardDto getBoardDetail(Long bno) {
        BoardDto dto = new BoardDto();
        try {
            Optional<Board> board = boardRepository.findById(bno);
            if (board.isPresent()) {
                dto = BoardDto.of(board.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    public boolean modifyBoard(BoardDto boardDto) {
        try {
            Member member = memberService.memberIdFindMember();
            Optional<Board> board = boardRepository.findById(boardDto.getBno());
            if (board.isPresent()) {
                Board b = Board.builder()
                        .memberbno(member)
                        .bno(board.get().getBno())
                        .bdate(LocalDateTime.now())
                        .category(board.get().getCategory())
                        .content(boardDto.getContent())
                        .title(boardDto.getTitle())
                        .build();
                boardRepository.save(b);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBoard(Long bno) {
        try {
            Optional<Board> board = boardRepository.findById(bno);
            if (board.isPresent()) {
                Optional<Comment> comment = commentRepository.findByBoardcno(board.get());
                comment.ifPresent(value -> commentRepository.deleteById(value.getCno()));
                boardRepository.deleteById(bno);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createComment(CommentDto commentDto) {
        Member member = memberService.memberIdFindMember();

        try {
            Optional<Board> board = boardRepository.findById(commentDto.getBoard().getBno());
            if (board.isPresent()) {
                Board b = board.get();
                b.setTf(TF.TRUE);
                log.info("결과확인 comment : "+commentDto);
                boardRepository.save(b);

                Comment c = Comment.builder()
                        .comment(commentDto.getComment())
                        .boardcno(board.get())
                        .membercno(member)
                        .build();
                commentRepository.save(c);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifyComment(CommentDto commentDto){
        Member member = memberService.memberIdFindMember();
        try {
            Optional<Board> board = boardRepository.findById(commentDto.getBoard().getBno());
            if (board.isPresent()) {
                Comment comment = Comment.builder()
                        .cno(commentDto.getCno())
                        .comment(commentDto.getComment())
                        .boardcno(board.get())
                        .membercno(member)
                        .build();
                commentRepository.save(comment);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CommentDto selectCnoComment(Long bno) {
        CommentDto commentDto = new CommentDto();
        try {
            Optional<Board> board = boardRepository.findById(bno);
            if (board.isPresent()) {
                Optional<Comment> comment = commentRepository.findByBoardcno(board.get());
                if (comment.isPresent()) {
                    return CommentDto.of(comment.get());
                } else {
                    commentDto.setComment("게시된 답변이 없습니다.");
                }
            }else {
                commentDto.setComment("게시된 질문이 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            commentDto.setComment("오류발생");
        }
        return commentDto;
    }

    public List<MemberResDto> allUserTf(){
        List<MemberResDto> list = new ArrayList<>();
        try {
            List<Member> members = memberRepository.findByAuthority(Authority.ROLL_USER);
            for (Member m : members) {
                list.add(MemberResDto.of(m));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}