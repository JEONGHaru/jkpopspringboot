package com.haru.jkpop.repository;

import com.haru.jkpop.dto.BoardDTO;
import com.haru.jkpop.entity.Board;
import com.haru.jkpop.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositotyTests {

    @Autowired
    private BoardRepositoty boardRepositoty;

    @Test
    public void testRegister(){
        IntStream.rangeClosed(101,200).forEach(i->{
            Member member = Member.builder().email("user55@aaa.com").build();
            Board board = Board.builder()
                    .title("test Title..." + i)
                    .content("test Content..." + i)
                    .writer(member)
                    .build();
            boardRepositoty.save(board);
        });
    }

    @Transactional
    @Test
    public void testRead1(){
        Optional<Board> result = boardRepositoty.findById(100L);

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepositoty.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;

        System.out.println("----------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply(){
        List<Object[]> result = boardRepositoty.getBoardWithReply(100L);
        for (Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepositoty.getBoardWithReplyCount(pageable);
        result.get().map(row -> (Object[]) row).map(Arrays::toString).forEach(System.out::println);
    }

    @Test
    public void testRead3(){
        Object result = boardRepositoty.getBoardByBno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
        boardRepositoty.search1();
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepositoty.searchPage("t","1",pageable);
    }


}