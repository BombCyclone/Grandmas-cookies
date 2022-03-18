package com.lutheroaks.tacoswebsite.comment;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.tickets.Tickets;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// This class contains the attributes and getters/setters for the Comment table in the database
@Entity
@Data
@Table(name = "Comment")
@RequiredArgsConstructor
public class Comment implements Serializable {

    @Column(nullable = false, unique = false, length = 300)
    @NonNull private String content;

    @Column(nullable = false, unique = false, length = 300, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timeStamp;

    @Id
    @ManyToOne
    @JoinColumn(name="ticket_num")
    private transient Tickets ticketNum;

    @Id
    @ManyToOne
    @JoinColumn(name="memberId")
    private transient Member memberId;

}

