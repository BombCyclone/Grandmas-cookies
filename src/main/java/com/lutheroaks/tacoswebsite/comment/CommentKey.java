package com.lutheroaks.tacoswebsite.comment;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Embeddable;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.ticket.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CommentKey implements Serializable {
    
    private Timestamp timeStamp;

    private transient Ticket ticket;

    private transient Member member;
}
