package com.haru.jkpop.service;

import com.haru.jkpop.dto.ReplyDTO;
import com.haru.jkpop.entity.Board;
import com.haru.jkpop.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO dto);
    List<ReplyDTO> getList(Long bno);
    void modify(ReplyDTO dto);
    void remove(Long rno);

    //ReplyDTO를 Reply객체로 반환 Board객체의 처리가 수반됨
    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder().bno(dto.getBno()).build();
        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
        return reply;
    }

    //Reply객체를 ReplyDTO객체로 변환 Board 객체가 필요하지 않으므로 게시물 번호만 처리
    default ReplyDTO entityToDto(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }
}
