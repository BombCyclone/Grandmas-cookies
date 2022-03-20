package com.lutheroaks.tacoswebsite.comment;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.ticket.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Comment table in the database
@Entity
@Table(name = "Comment")
@IdClass(CommentKey.class)
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {

    @Column(nullable = false, unique = false, length = 300)
    @NonNull private String content;

    @Id
    @Column(nullable = false, unique = false, length = 300)
    @NonNull Date timeStamp;

    @Id
    @ManyToOne
    @JoinColumn(name="ticket", nullable = false)
    @NonNull private Ticket ticket;

    @Id
    @ManyToOne
    @JoinColumn(name="member", nullable = false)
    @NonNull private Member member;

}

