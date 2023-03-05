package com.haru.jkpop.service;

import com.haru.jkpop.dto.BoardDTO;
import com.haru.jkpop.dto.PageRequestDTO;
import com.haru.jkpop.dto.PageResultDTO;
import com.haru.jkpop.entity.Board;
import com.haru.jkpop.entity.Member;
import com.haru.jkpop.repository.BoardRepositoty;
import com.haru.jkpop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepositoty boardRepositoty;
    private final ReplyRepository replyRepository;
    @Override
    public Long register(BoardDTO dto) {

        log.info("BoardService --------------- dto : " +dto);
        Board board = dtoToEntity(dto);

        boardRepositoty.save(board);
        return dto.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info("BoardService ---------------- PageRequestDTO : " + pageRequestDTO);
        Function<Object[],BoardDTO> fn = (en -> entityToDto((Board) en[0],(Member) en[1],(Long) en[2]));
        //Page<Object[]> result = boardRepositoty.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepositoty.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );
        return new PageResultDTO<>(result,fn);
    }

    public BoardDTO get(Long bno){
        Object result = boardRepositoty.getBoardByBno(bno);
        Object[] arr = (Object[]) result;

        return entityToDto((Board) arr[0],(Member) arr[1],(Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepositoty.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO dto) {
        Optional<Board> result = boardRepositoty.findById(dto.getBno());
        if(result.isPresent()){
             Board board = result.get();
             board.changeTitle(dto.getTitle());
             board.changeContent(dto.getContent());
             boardRepositoty.save(board);
        }
    }
}
