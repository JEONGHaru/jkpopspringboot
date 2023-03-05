package com.haru.jkpop.service;

import com.haru.jkpop.dto.BoardDTO;
import com.haru.jkpop.dto.PageRequestDTO;
import com.haru.jkpop.dto.PageResultDTO;
import com.haru.jkpop.entity.Board;
import com.haru.jkpop.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);
    void modify(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDto(Board board,Member member,Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }
}
