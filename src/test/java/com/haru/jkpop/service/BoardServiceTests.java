package com.haru.jkpop.service;

import com.haru.jkpop.dto.BoardDTO;
import com.haru.jkpop.dto.PageRequestDTO;
import com.haru.jkpop.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("Test...")
                .content("Test...")
                .writerEmail("user5@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);
        for (BoardDTO dto : result.getDtoList()){
            System.out.println(dto);
        }
    }

    @Test
    public void testGet(){
        Long bno = 100L;
        BoardDTO dto = boardService.get(bno);
        System.out.println(dto);
    }

    @Test
    public void testRemove(){
        Long bno = 99L;
        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                .bno(2L)
                .title("제목변경합니다")
                .content("내용 변경합니다")
                .build();
        boardService.modify(dto);
    }

}