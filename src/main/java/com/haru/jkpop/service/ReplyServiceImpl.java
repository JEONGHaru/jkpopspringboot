package com.haru.jkpop.service;

import com.haru.jkpop.dto.ReplyDTO;
import com.haru.jkpop.entity.Board;
import com.haru.jkpop.entity.Reply;
import com.haru.jkpop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO dto) {

        Reply reply = dtoToEntity(dto);
        replyRepository.save(reply);

        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {

        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return result.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO dto) {

        Reply reply = dtoToEntity(dto);
        replyRepository.save(reply);

    }


    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);

    }
}
