package com.lutheroaks.tacoswebsite.entities.comment;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Comment table in the database
@Entity
@Table(name = "Comment")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(nullable = false, unique = false, length = 2000)
    @NonNull private String content;

    @Column(nullable = false, unique = false, length = 100)
    @NonNull private Timestamp timeStamp;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="ticket", nullable = false)
    @NonNull private Ticket ticket;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="member", nullable = false)
    @NonNull private Member member;

}

