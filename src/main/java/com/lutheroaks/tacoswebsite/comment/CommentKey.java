package com.lutheroaks.tacoswebsite.comment;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.ticket.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class CommentKey implements Serializable {
    
    private Date timeStamp;

    private transient Ticket ticket;

    private transient Member member;
}
