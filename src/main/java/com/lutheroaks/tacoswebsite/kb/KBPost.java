package com.lutheroaks.tacoswebsite.kb;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.lutheroaks.tacoswebsite.member.Member;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "KBPost")
@IdClass(KBPostKey.class)
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class KBPost {

    @Column(nullable = false, unique = true, length = 50)
    @NonNull private String title;

    @Column(nullable = false, unique = true, length = 300)
    @NonNull private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postNum;

    @Id
    @ManyToOne
    @JoinColumn(name="member", nullable = false)
    @NonNull private Member member;

    @Column(nullable = false, length = 50)
    @ElementCollection(targetClass = String.class)
    @NonNull private List<String> tags;

    @Id
    @Column(nullable = false, unique = false, length = 300)
    @NonNull Date timeStamp;
    
}
