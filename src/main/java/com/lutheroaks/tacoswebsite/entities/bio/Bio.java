package com.lutheroaks.tacoswebsite.entities.bio;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutheroaks.tacoswebsite.entities.member.Member;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Bio")
@Data
@RequiredArgsConstructor
public class Bio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private int bioId;

    @Column(unique = false, length = 50)
    @Nullable
    private String major;

    @Column(unique = false, length = 50)
    @Nullable
    private String hometown;

    @Column(unique = false, length = 500)
    @Nullable
    private String backgroundInfo;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Nullable
    private byte[] profilePicture;

    @OneToOne(optional = false)
    @JoinColumn(name = "memberId", nullable = false)
    @JsonIgnore
    private Member member;

}
