package com.haru.jkpop.repository;

import com.haru.jkpop.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("user" + i+"@aaa.com")
                    .paswword("1111")
                    .name("ln"+i)
                    .build();
            memberRepository.save(member);

        });
    }
}