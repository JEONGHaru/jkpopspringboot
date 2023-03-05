package com.haru.jkpop.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends Base {
    @Id
    @Column(length = 50)
    private String email;
    @Column(length = 100,nullable = false)
    private String paswword;
    @Column(length = 50,nullable = false)
    private String name;



}
